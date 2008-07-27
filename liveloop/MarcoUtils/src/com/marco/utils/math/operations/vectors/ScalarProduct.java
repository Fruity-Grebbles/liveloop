package com.marco.utils.math.operations.vectors;

import com.marco.utils.math.Operation;
import com.marco.utils.math.objects.Vector;
import com.marco.utils.math.operations.Ops;

public class ScalarProduct implements Operation{

	public Object calculate(Object... params) {
		Vector v1=(Vector) params[0];
		Vector v2=(Vector) params[1];
		if(v1.getDimension()!=v2.getDimension())
			throw new IllegalArgumentException();
		Object result=new Double(0);
		for(int i=0;i<v1.getDimension();i++){
			Object v1i=v1.get(i);
			Object v2i=v2.get(i);
			Object resi=Ops.MUL.calculate(v1i,v2i);
			result=Ops.ADD.calculate(result,resi);
		}
		return result;		
	}


}
