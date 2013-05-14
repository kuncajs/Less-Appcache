package cz.muni.fi.lessappcache.parser.modules;

public enum ModuleControl {
    /**
     * CONTINUE tells the parser that module did not parse the line or the parser
     * should reprocess the same line
     */
    CONTINUE,
    /**
     * STOP tells the parser that modules parsed the line and no further
     * processing should be done
     */
    STOP,
    /**
     * REPARSE tells the parser that modules parsed the line and processing of this line
     * should be stopped but lines produced in output should be reparsed
     */
    REPARSE
}
