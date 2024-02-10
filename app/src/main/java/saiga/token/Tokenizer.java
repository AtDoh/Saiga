package saiga.token;

import java.util.Enumeration;

public abstract class Tokenizer implements Enumeration<Token>{
    /**
     * 입력으로 부터 Token 들을 분리할 기준이 되는 문자들.
     */
    private String delimeters;

    /**
     * 
     */
    private String quotationMarks;

    private boolean quotation;

    /**
     * {@link delimeters} 중에 16bit 로 표현할 수 없는 surrogate 문자영역에 해당하는 문자가 있는지 여부.
     */
    private boolean hasSurrogates;

    /**
     * delimeter의 CodePoint 중 최댓값을 저장.
     */
    private int maxDelimeterCodePoint;

    /**
     * delimeter 의 CodePoint 를 저장하는 배열.
     */
    private int[] delimeterCodePoints;

    public Tokenizer() {
        this("\t\n\r\f","\"'");
    }

    public Tokenizer(String delimeters) {
        this(delimeters, "\"'");
    }

    public Tokenizer(String delimeters, String quotationMarks) {
        this.delimeters = delimeters;
        this.quotationMarks = quotationMarks;
        this.quotation = false;

        this.setMaxDelimeterCodePoint();
    }
    /**
     * {@link delimeterCodePoints} 를 초기화.
     * {@link maxDelimeterCodePoint} 를 초기화.
     * {@link hasSurrogates} 를 초기화.
     */
    public void setMaxDelimeterCodePoint() {
        // 주어진 delimeter 가 없는 경우 
        if(this.delimeters == null) {
            this.maxDelimeterCodePoint = 0;
            return;
        }
        int count = 0;
        int mx = 0;
        int c;

        // delimeters 에 포함된 delimeter 문자 중 surrogate 쌍으로 표현되는 문자가 있는지 확인.
        // delimeter 의 총 개수를 카운팅.
        // delimeter 중 가장 큰 codePoint 의 값을 찾는다.
        for(int i = 0; i < delimeters.length(); i += Character.charCount(c)) {
            c = this.delimeters.charAt(i);
            if(0xD800 <= c && c <= 0xDFFF) {
                c = this.delimeters.codePointAt(i);
                this.hasSurrogates = true;
            }
            if(mx < c) {
                mx = c;
            }
            ++count;
        }

        // 카운팅된 delimeter 의 개수만큼의 크기를 가지는 delimeter 의 codePoint 를 저장하는 배열 생성.
        // delimeter 들의 codePoint 를 delimeterCodePoints 에 저장.
        this.delimeterCodePoints = new int[count];
        for(int j = 0, i = 0; i < count; j += Character.charCount(c), ++i) {
            c = this.delimeters.codePointAt(j);
            this.delimeterCodePoints[i] = c;
        }
    }

    public abstract boolean hasNextToken();

    public abstract Token nextToken();
}
