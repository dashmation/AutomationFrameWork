package com.test.TestScript;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.qa.api.PerformOperations;
import com.qa.api.PerformOperations.SETInUSE;

public class MainRun {

	@Test(groups = "sync")
	public void method1() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=false");
			Thread.sleep(2000);
			Reporter.log("method1:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "sync")
	public void method2() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=true");
			Thread.sleep(2000);
			Reporter.log("method2:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "sync")
	public void method3() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=false");
			Thread.sleep(2000);
			Reporter.log("method3:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "sync")
	public void method4() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=true");
			Thread.sleep(2000);
			Reporter.log("method4:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "sync")
	public void method5() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=false");
			Thread.sleep(2000);
			Reporter.log("method5:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "sync")
	public void method6() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=true");
			Thread.sleep(2000);
			Reporter.log("method6:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "sync")
	public void method7() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=true");
			Thread.sleep(2000);
			Reporter.log("method7:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "sync")
	public void method8() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=false");
			Thread.sleep(2000);
			Reporter.log("method8:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "synced")
	public void method9() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=false");
			Thread.sleep(600000);
			Reporter.log("method9:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:"+perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

	@Test(groups = "synced")
	public void method10() {
		PerformOperations perform = new PerformOperations();
		try {
			perform.getDataFromServer("inuse=false&reg=true&2fa=true&npah=false");
			Thread.sleep(2000);
			Reporter.log("method10:======" + perform.getMsisdn() + " at "
					+ String.valueOf(System.currentTimeMillis()).substring(6));
			Reporter.log("id is:"+perform.getId(), true);
			Reporter.log("reg is:"+perform.getReg(), true);
			Reporter.log("inuse is:"+perform.getInuse(), true);
			Reporter.log("email is:"+perform.getEmail(), true);
			Reporter.log("2fa is:"+perform.getFa(), true);
			Reporter.log("npah is:"+perform.getNpah(), true);
			Reporter.log("multidev is:" + perform.getMultidev(), true);
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			perform.setInuse(SETInUSE.F);
			Reporter.log("After Finally:" + "inuse is:"+perform.getInuse(), true);
		}
	}

}