package com.marco.utils.math.operations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.marco.utils.math.DefaultVector;
import com.marco.utils.math.Operation;
import com.marco.utils.math.objects.Vector;
import com.marco.utils.math.operations.vectors.ScalarProduct;
import com.marco.utils.math.operations.vectors.VectorOpVector;
import com.marco.utils.math.operations.vectors.VectorOperation;

public class Ops {
	
	
		
	public static Operation invertArgs(final Operation op){
		return new Operation(){
			public Object calculate(Object... params) {
				List l=new ArrayList();
				Collections.addAll(l, params);
				Collections.rotate(l, 1);
				Object[] params2=l.toArray(new Object[l.size()]);
				return op.calculate(params2);
			}			
		};
	}
	
	public static Operation fromGraph(final Object[][] graph){		
		return new Operation(){
			protected boolean sameArgs(Object[] tab,Object[] params){
				if(params.length!=tab.length-1)//last field is the operation
					return false;
				for(int j=0;j<params.length;j++){
					Class ci=(Class)tab[j];
					if(!ci.isInstance(params[j]))
						return false;					
				}
				return true;
			}
			public Object calculate(Object... params) {
				for(int i=0;i<graph.length;i++){
					Object[] tab=graph[i];
					if(sameArgs(tab, params)){
						Operation op=(Operation)tab[tab.length-1];
						return op.calculate(params);						
					}					
				}
				ArrayList l=new ArrayList();
				Collections.addAll(l, params);
				throw new IllegalArgumentException("Invalid arguments type or number of arguments.Cannot find an operation to calculate "+l);
			}			
		};
		
	}
	
	
	public static final Operation NUMBER_ADD=new Operation(){
		public Object calculate(Object... params) {
			Number n=(Number) params[0];
			Number c=(Number) params[1];
			Number res=new Double(n.doubleValue()+c.doubleValue());
			return res;
		}		
	};
	
	public static final Operation NUMBER_MUL=new Operation(){
		public Object calculate(Object... params) {
			Number n=(Number) params[0];
			Number c=(Number) params[1];
			Number res=new Double(n.doubleValue()*c.doubleValue());
			return res;
		}		
	};
	
	public static final Operation SCALAR_PRODUCT=new ScalarProduct();
	
		
	
	//TODO : voir si y a pas moyen de définir la fonction avec lambalise et o
	//ca ferait mieux.
	public static final Operation VECT_ADDSCALAR=new Operation(){
		public Object calculate(Object... params) {
			Vector v=(Vector) params[0];
			final Number scalar=(Number) params[1];
			Operation plusScalar=new Operation(){
				public Object calculate(Object... params) {
					Object vectorEle=params[0];
					return ADD.calculate(vectorEle,scalar);
				}				
			};
			return new VectorOperation(plusScalar).calculate(v);			
		}	
	};
	public static final Operation VECT_MULSCALAR=new Operation(){
		public Object calculate(Object... params) {
			Vector v=(Vector) params[0];
			final Object scalar= params[1];
			Operation plusScalar=new Operation(){
				public Object calculate(Object... params) {
					Object vectorEle=params[0];
					return MUL.calculate(vectorEle,scalar);
				}				
			};
			return new VectorOperation(plusScalar).calculate(v);			
		}	
	};
	
	public static Operation getFromType(final OpsTypes type){
		return new Operation(){
			public Object calculate(Object... params) {				
				Number n=(Number) params[0];
				Number res;				
				switch (type) {
				case LOG:
					res=Math.log(n.doubleValue());
					break;
				case SQR:
					res=Math.sqrt(n.doubleValue());
					break;
				case INV :
					res=1/n.doubleValue();
					break;
				case NEG :
					res=-n.doubleValue();
					break;
				default:
					throw new IllegalArgumentException("Unknown operation : "+type);
				}
				return res;
			}
		};
	}
	
	
	
	
	/*protected static Object[][] graphADD={{Vector.class,Vector.class,VECT_ADD},
		{Vector.class,Number.class,VECT_ADDSCALAR},
		{Number.class,Vector.class,invertArgs(VECT_ADDSCALAR)},
		{Number.class,Number.class,NUMBER_ADD}
		};
	
	
	protected static Object[][] graphMUL={{Vector.class,Vector.class,VECT_MUL},
		{Vector.class,Number.class,VECT_MULSCALAR},
		{Number.class,Vector.class,invertArgs(VECT_MULSCALAR)},
		{Number.class,Number.class,NUMBER_MUL}
		};
	*/
	
	protected static Object[][] graphOP(OpsTypes type){
		Object[][] graph={	{Vector.class,vectOp(type)},
				{Number.class,getFromType(type)}};
		return graph;
		
	}
	protected static Operation vectOp(final OpsTypes type){
		Operation res=new VectorOperation(new Operation(){
			public Object calculate(Object... params) {
				return fromGraph(graphOP(type)).calculate(params);
			}
		});
		return res;
	}
	//public static final Operation MUL=fromGraph(graphMUL);
	public static final Operation ADD=new Operation(){
		public Object calculate(Object... params) {
			Object o1=params[0];
			Object o2=params[1];
			if(Number.class.isInstance(o1)){
				if(Number.class.isInstance(o2)){
					return NUMBER_ADD.calculate(o1,o2);
				}else if(Vector.class.isInstance(o2)){
					return invertArgs(VECT_ADDSCALAR).calculate(o1,o2);
				}				
			}else if(Vector.class.isInstance(o1)){
				if(Number.class.isInstance(o2)){
					return VECT_ADDSCALAR.calculate(o1,o2);
				}else if(Vector.class.isInstance(o2)){
					return VECT_ADD.calculate(o1,o2);
				}
			}
			throw new IllegalArgumentException("Invalid parameters");
			
		}		
	};
	
	public static final Operation MUL=new Operation(){
		public Object calculate(Object... params) {
			Object o1=params[0];
			Object o2=params[1];
			if(Number.class.isInstance(o1)){
				if(Number.class.isInstance(o2)){
					return NUMBER_MUL.calculate(o1,o2);
				}else if(Vector.class.isInstance(o2)){
					return invertArgs(VECT_MULSCALAR).calculate(o1,o2);
				}				
			}else if(Vector.class.isInstance(o1)){
				if(Number.class.isInstance(o2)){
					return VECT_MULSCALAR.calculate(o1,o2);
				}else if(Vector.class.isInstance(o2)){
					Vector v1=(Vector)o1;
					Vector v2=(Vector)o2;
					if(v1.getDimension()==v2.getDimension())
						return SCALAR_PRODUCT.calculate(o1,o2);
					else
						return VECT_MULSCALAR.calculate(o1,o2);
				}
			}
			throw new IllegalArgumentException("Invalid parameters");
					
		}		
	};

	public static final Operation VECT_MUL=lambalise(new VectorOpVector(), MUL);
	public static final Operation VECT_ADD=lambalise(new VectorOpVector(), ADD);
	
	public static Operation o(final Operation p1,final Operation p2){
		return new Operation(){
			public Object calculate(Object... params) {
				return p1.calculate(p2.calculate(params));
			}				
		};
	}
	
	//p1 o p2 = f: x -> p1(p2(x))
	public static final Operation O=new Operation(){
		public Object calculate(Object... params) {
			final Operation p1=(Operation) params[0];
			final Operation p2=(Operation) params[1];
			return o(p1,p2);
		}	
	};
	
	public static final Operation IDENT=new Operation(){
		public Object calculate(Object... params) {
			return params[0];
		}		
	};
	
	//This function take an Operation that have n arguments (n>=2) and return
	//a function with n-1 argument that call the first one with all the parameters
	//EX  :
	//Operation addOne=lambalise(ADD,1);
	//Object res=addOne.calculate(3);
	//->return 4;
	public static final Operation lambalise(final Operation op,final Object firstParam){		
		return new Operation(){
			public Object calculate(Object... params) {
				ArrayList l=new ArrayList();
				l.add(firstParam);
				Collections.addAll(l, params);
				Object[] all=l.toArray(new Object[l.size()]);
				return op.calculate(all);
			}			
		};		
	}
	/*public static final Operation lambalise(final Operation op,final Object param,int paramIndex){		
		return new Operation(){
			public Object calculate(Object... params) {
				ArrayList l=new ArrayList();
				Collections.in
				Collections.addAll(l, params);
				
				Object[] all=l.toArray(new Object[l.size()]);
				return op.calculate(all);
			}			
		};		
	}*/
	
	public static final Operation LAMBA=new Operation(){
		public Object calculate(Object... params) {
			return lambalise((Operation)params[0], params[1]);
		}
	};
	public static final Operation DIM=new Operation(){
		public Object calculate(Object... params) {
			Vector v=(Vector) params[0];
			return v.getDimension();
		}
	};
	public static final Operation GET=new Operation(){
		public Object calculate(Object... params) {
			Vector v=(Vector) params[0];
			int i=(Integer) params[1];
			return v.get(i);
		}
	};
	public static final Operation DIV=new Operation(){
		public Object calculate(Object... params) {
			Object d=getFromType(OpsTypes.INV).calculate(params[1]);
			return MUL.calculate(params[0],d);
		}	
	};
	
	
	public static final Operation LESS=new Operation(){
		public Object calculate(Object... params) {
			Object d=getFromType(OpsTypes.NEG).calculate(params[1]);
			return ADD.calculate(params[0],d);
		}	
	};
	
	public static final Operation VECT_DO=new Operation(){
		public Object calculate(Object... params) {
			Operation op=(Operation) params[0];
			Vector v=(Vector) params[1];			
			Vector res=new DefaultVector(v.getDimension());
			for(int i=0;i<v.getDimension();i++){
				Object c=v.get(i);
				Object resi=op.calculate(c);
				res.set(i, resi);
			}
			return res;
		}		
	};
	public static final Operation SUM=new Operation(){
		public Object calculate(Object... params) {
			int i0=(Integer)params[0];
			int n=(Integer)params[1];
			Operation operation=(Operation) params[2];
			
			Object s0=operation.calculate(new Integer(i0));
			Object res=s0;
			for(int i=i0+1;i<=n;i++){
				Object si=operation.calculate(i);
				res=Ops.ADD.calculate(res,si);
			}
			return res;
		}		
	};
	public static final Operation AVE=new Operation(){
		public Object calculate(Object... params) {
			int i0=(Integer)params[0];
			int n=(Integer)params[1];
			Operation operation=(Operation) params[2];
			Object sum=SUM.calculate(i0,n,operation);
			Object less=LESS.calculate(n,i0);
			less=ADD.calculate(less,1);
			return DIV.calculate(sum,less);
		}		
	};
	
	public static final Operation AVE_VECT=new Operation(){
		public Object calculate(Object... params) {
			Vector v=(Vector) params[0];
			return AVE.calculate(0,v.getDimension()-1,lambalise(GET, v));
		}		
	};
	
	
	public static final Operation TO_VECT=new Operation(){
		public Object calculate(Object... params) {
			int i0=(Integer)params[0];
			int n=(Integer)params[1];
			Operation operation=(Operation) params[2];
			
			Object s0=operation.calculate(new Integer(i0));
			Vector v=new DefaultVector();
			for(int i=i0;i<=n;i++){
				Object si=operation.calculate(i);
				v.set(i, si);
			}
			return v;
		}		
	};
	
	
	
	public static void main(String[] args) {
		Operation toSum=new Operation(){
			public Object calculate(Object... params) {
				int i=(Integer)params[0];
				return i+1;
			}			
		};
		Object result=SUM.calculate(0,2,toSum);
		Object vect=TO_VECT.calculate(0,2,toSum);
		Operation geti=lambalise(GET, vect);
		Object moy=AVE.calculate(0,2,geti);
		System.out.println(result+" "+vect+" "+moy);
	}
	
	
	
	
}
