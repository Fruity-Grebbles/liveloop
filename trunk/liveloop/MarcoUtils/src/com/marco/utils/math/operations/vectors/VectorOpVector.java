package com.marco.utils.math.operations.vectors;

import com.marco.utils.math.DefaultVector;
import com.marco.utils.math.Operation;
import com.marco.utils.math.objects.Vector;
import com.marco.utils.math.operations.Ops;

public class VectorOpVector implements Operation {

	public Object calculate(Object... params) {
		Operation op=(Operation) params[0];
		Vector v1=(Vector) params[1];
		Vector v2=(Vector) params[2];
		if(v1.getDimension()!=v2.getDimension())
			throw new IllegalArgumentException("Dimensions must be the same");
		Vector res=new DefaultVector();
		for(int i=0;i<v1.getDimension();i++){
			Object c=v1.get(i);
			Object th=v2.get(i);
			Object resi=op.calculate(c,th);
			res.set(i, resi);
		}
		return res;
	}

}
