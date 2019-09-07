package com.gigety.ur.util.validation;

/**
 * 
 * FormValidationGroup Validation group - this allows us to separate validations.
 * Initial purpose is to solve issue of Hibernate validating encoded passwords
 * on persist. So by using validation groups we can set @ValidPassword to only
 * validate for specified group(s)
 *
 */
public interface FormValidationGroup {

}
