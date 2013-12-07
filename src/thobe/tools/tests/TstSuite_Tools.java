/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import thobe.tools.tests.log.TstSuite_Log;

/**
 * @author Thomas Obenaus
 * @source TstSuite_Tools.java
 * @date 29.03.2012
 */
@RunWith ( Suite.class)
@Suite.SuiteClasses (
{ Tst_MakeUniqueStrings.class, TstSuite_Log.class })
public class TstSuite_Tools
{

}
