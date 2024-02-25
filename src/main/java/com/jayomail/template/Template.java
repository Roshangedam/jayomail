package com.jayomail.template;


/**
 * Template is an abstract class representing a generic template for generating content.
 * It defines methods for setting the template content, replacing placeholder values,
 * building the final content, and retrieving the content.
 */
public abstract class Template {
    /** The content of the template. */
    protected String content;

    /**
     * Sets the content of the template.
     * @param content The content to set as the template.
     * @return The Template object.
     */
    public abstract Template setTemplate(String content);

    /**
     * Sets the value of a placeholder in the template.
     * @param key The placeholder key.
     * @param value The value to replace the placeholder with.
     * @return The Template object.
     */
    public abstract Template setValue(String key, String value);

    /**
     * Builds the final content after replacing all placeholder values.
     * @return The final content.
     */
    public abstract String build();
    
    /**
     * Retrieves the content of the template.
     * @return The content of the template.
     */
    public abstract String getContent();
}
