package me.ibore.router.rules;

@SuppressWarnings("unused")
public interface Element {
    /**
     * Check whether the source exits.
     *
     * @return a Rule instance
     */
    Rule exists();

    /**
     * Check whether the source equals to the {@param value}
     * using {@link String#equals} method.
     *
     * @param value value
     * @return a Rule instance
     */
    Rule is(String value);

    /**
     * Check whether the source has the {@param value} as prefix
     * using {@link String#startsWith} method.
     *
     * @param value value
     * @return a Rule instance
     */
    Rule startsWith(String value);

    /**
     * Check whether the source has the {@param value} as suffix
     * using {@link String#endsWith} method.
     *
     * @param value value
     * @return a Rule instance
     */
    Rule endsWith(String value);

    /**
     * Check whether the source contains the {@param value} as a substring
     * using {@link String#endsWith} method.
     *
     * @param value value
     * @return a Rule instance
     */
    Rule contains(String value);

    /**
     * Check whether the source is one of the {@param values}.
     *
     * @param values value
     * @return a Rule instance
     */
    Rule in(String... values);
}
