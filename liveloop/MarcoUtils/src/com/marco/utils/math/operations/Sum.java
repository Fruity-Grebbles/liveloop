package com.marco.utils.math.operations;

import com.marco.utils.math.Operation;

public class Sum implements Operation {

	protected Operation operation;
	public Sum(Operation operation){
		this.operation=operation;
	}
	public Object calculate(Object... params) {
		int i0=(Integer)params[0];
		int n=(Integer)params[1];
		Object s0=operation.calculate(new Integer(i0));
		Object res=s0;
		for(int i=i0+1;i<=n;i++){
			Object si=operation.calculate(i);
			res=Ops.ADD.calculate(res,si);
		}
		return res;
	}

}
