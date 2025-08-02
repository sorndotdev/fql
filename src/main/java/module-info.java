/**
 * Module declaration for the Financial Query Language (FQL) library.
 *
 * <p>This module exports the {@code dev.sorn.fql.api} package, which contains the public API accessible to consumers of
 * the library.</p>
 *
 * <p>All other packages within this module are kept internal and not exported.</p>
 */
module dev.sorn.fql {
    requires org.antlr.antlr4.runtime;
    exports dev.sorn.fql.api;
}