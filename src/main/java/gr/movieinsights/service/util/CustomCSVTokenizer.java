package gr.movieinsights.service.util;

import org.supercsv.comment.CommentMatcher;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.AbstractTokenizer;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Reader;
import java.util.List;


public class CustomCSVTokenizer extends AbstractTokenizer {
    private static final char NEWLINE = '\n';
    private static final char SPACE = ' ';
    private final StringBuilder currentColumn = new StringBuilder();
    private final StringBuilder currentRow = new StringBuilder();
    private final int quoteChar;
    private final int delimeterChar;
    private final boolean surroundingSpacesNeedQuotes;
    private final boolean ignoreEmptyLines;
    private final CommentMatcher commentMatcher;
    private final int maxLinesPerRow;

    public CustomCSVTokenizer(Reader reader, CsvPreference preferences) {
        super(reader, preferences);
        this.quoteChar = preferences.getQuoteChar();
        this.delimeterChar = preferences.getDelimiterChar();
        this.surroundingSpacesNeedQuotes = preferences.isSurroundingSpacesNeedQuotes();
        this.ignoreEmptyLines = preferences.isIgnoreEmptyLines();
        this.commentMatcher = preferences.getCommentMatcher();
        this.maxLinesPerRow = preferences.getMaxLinesPerRow();
    }

    public boolean readColumns(List<String> columns) throws IOException {
        if (columns == null) {
            throw new NullPointerException("columns should not be null");
        } else {
            columns.clear();
            this.currentColumn.setLength(0);
            this.currentRow.setLength(0);

            String line;
            do {
                do {
                    line = this.readLine();
                    if (line == null) {
                        return false;
                    }
                } while (this.ignoreEmptyLines && line.length() == 0);
            } while (this.commentMatcher != null && this.commentMatcher.isComment(line));

            this.currentRow.append(line);
            CustomCSVTokenizer.TokenizerState state = CustomCSVTokenizer.TokenizerState.NORMAL;
            int quoteScopeStartingLine = -1;
            int potentialSpaces = 0;
            int charIndex = 0;

            while (true) {
                do {
                    boolean endOfLineReached = charIndex == line.length();
                    if (!endOfLineReached) {
                        break;
                    }

                    if (CustomCSVTokenizer.TokenizerState.NORMAL.equals(state)) {
                        if (!this.surroundingSpacesNeedQuotes) {
                            appendSpaces(this.currentColumn, potentialSpaces);
                        }

                        columns.add(this.currentColumn.length() > 0 ? this.currentColumn.toString() : null);
                        return true;
                    }

                    this.currentColumn.append('\n');
                    this.currentRow.append('\n');
                    charIndex = 0;
                    if (this.maxLinesPerRow > 0 && this.getLineNumber() - quoteScopeStartingLine + 1 >= this.maxLinesPerRow) {
                        String msg = this.maxLinesPerRow == 1 ? String.format("unexpected end of line while reading quoted column on line %d", this.getLineNumber()) : String.format("max number of lines to read exceeded while reading quoted column beginning on line %d and ending on line %d", quoteScopeStartingLine, this.getLineNumber());
                        throw new SuperCsvException(msg);
                    }

                    if ((line = this.readLine()) == null) {
                        throw new SuperCsvException(String.format("unexpected end of file while reading quoted column beginning on line %d and ending on line %d", quoteScopeStartingLine, this.getLineNumber()));
                    }

                    this.currentRow.append(line);
                } while (line.length() == 0);

                char c = line.charAt(charIndex);
                if (CustomCSVTokenizer.TokenizerState.NORMAL.equals(state)) {
                    if (c == this.delimeterChar || c == '\t') {
                        if (!this.surroundingSpacesNeedQuotes) {
                            appendSpaces(this.currentColumn, potentialSpaces);
                        }

                        columns.add(this.currentColumn.length() > 0 ? this.currentColumn.toString() : null);
                        potentialSpaces = 0;
                        this.currentColumn.setLength(0);

                    } else if (c == ' ') {
                        ++potentialSpaces;
                    } else if (c == this.quoteChar) {
                        state = CustomCSVTokenizer.TokenizerState.QUOTE_MODE;
                        quoteScopeStartingLine = this.getLineNumber();
                        if (!this.surroundingSpacesNeedQuotes || this.currentColumn.length() > 0) {
                            appendSpaces(this.currentColumn, potentialSpaces);
                        }

                        potentialSpaces = 0;
                    } else {
                        if (!this.surroundingSpacesNeedQuotes || this.currentColumn.length() > 0) {
                            appendSpaces(this.currentColumn, potentialSpaces);
                        }

                        potentialSpaces = 0;
                        this.currentColumn.append(c);
                    }
                } else if (c == this.quoteChar) {
                    int nextCharIndex = charIndex + 1;
                    boolean availableCharacters = nextCharIndex < line.length();
                    boolean nextCharIsQuote = availableCharacters && line.charAt(nextCharIndex) == this.quoteChar;
                    if (nextCharIsQuote) {
                        this.currentColumn.append(c);
                        ++charIndex;
                    } else {
                        state = CustomCSVTokenizer.TokenizerState.NORMAL;
                        quoteScopeStartingLine = -1;
                    }
                } else {
                    this.currentColumn.append(c);
                }

                ++charIndex;
            }
        }
    }

    private static void appendSpaces(StringBuilder sb, int spaces) {
        for (int i = 0; i < spaces; ++i) {
            sb.append(' ');
        }

    }

    public String getUntokenizedRow() {
        return this.currentRow.toString();
    }

    private static enum TokenizerState {
        NORMAL,
        QUOTE_MODE;

        private TokenizerState() {
        }
    }
}
