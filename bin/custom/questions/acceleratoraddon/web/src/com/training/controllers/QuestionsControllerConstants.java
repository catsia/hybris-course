/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.controllers;

import com.training.model.QuestionsCMSComponentModel;

/**
 */
public interface QuestionsControllerConstants
{
	// implement here controller constants used by this extension
    interface Actions
    {
        interface Cms
        {
            String _Prefix = "/view/";
            String _Suffix = "Controller";
            String QuestionsCMSComponent = _Prefix + QuestionsCMSComponentModel._TYPECODE + _Suffix;
        }
    }
}
