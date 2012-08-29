package org.xulaing.tests;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestMyJava extends TestCase {
	public void testFind123(){
		String a = new MyJava().find123();
		Assert.assertNotNull(a);
	}
	public void testFind(){
		int a = new MyJava().find(10);
		Assert.assertEquals(10, a);
	}
}
