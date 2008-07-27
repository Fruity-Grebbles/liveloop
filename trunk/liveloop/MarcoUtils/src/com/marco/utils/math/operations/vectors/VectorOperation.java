package com.marco.utils.math.operations.vectors;

import com.marco.utils.math.DefaultVector;
import com.marco.utils.math.Operation;
import com.marco.utils.math.objects.Vector;

public class VectorOperation implements Operation {

	protected Operation op;
	public VectorOperation(Operation op){
		this.op=op;
	}
	public Object calculate(Object... params) {
		//Operation op=(Operation) params[0];
		Vector v=(Vector) params[0];
		
		Vector res=new DefaultVector(v.getDimension());
		for(int i=0;i<v.getDimension();i++){
			Object c=v.get(i);
			Object resi=op.calculate(c);
			res.set(i, resi);
		}
		return res;
	}
	
	
	

}
