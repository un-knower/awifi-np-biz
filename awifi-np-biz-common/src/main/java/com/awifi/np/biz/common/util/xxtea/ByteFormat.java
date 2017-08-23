package com.awifi.np.biz.common.util.xxtea;


public class ByteFormat {
    
    /** 空字符串 */
    private static final String EMPTY = "";
    /** */
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
    /** */
    private static final int ROW_BYTES = 16;
    /** */
    private static final int ROW_QTR1 = 3;
    /** */
    private static final int ROW_HALF = 7;
    /** */
    private static final int ROW_QTR2 = 11;
    
    /**
     * 字符集转换
     * @param bArray 
     * @return str
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2){
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * 将byte数组转换为十六进制文本
     * @param buf 
     * @return str
     */
    public static String toHex(byte[] buf) {
        if (buf == null || buf.length == 0) {
            return EMPTY;
        }
        StringBuilder out = new StringBuilder();
        for( int i=0; i< buf.length; i++ ){
            out.append( HEX[ (buf[i]>>4) & 0x0f ] ).append(HEX[ buf[i] & 0x0f ] ); 
        }
        return out.toString();
    }

    /**
     * 将十六进制文本转换为byte数组
     * @param str 
     * @return byte[]
     */
    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        }
        char[] hex = str.toCharArray();
        
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127){
                value -= 256;
            }
            raw[i] = (byte) value;
        }
        return raw;
    }

    /**
     * 将byte数组转换为格式化的十六进制文本
     * @param buf 
     * @return str
     */
    public static String dumpHex(byte[] buf) {
        if (buf == null) {
            return EMPTY;
        }
        return dumpHex(buf, 0, buf.length);
    }
    
    /**
     * 将byte数组转换为格式化的十六进制文本
     * @param buf 
     * @param offset 
     * @param numBytes 
     * @return str
     */
    public static String dumpHex(byte[] buf, int offset, int numBytes) {
        if (buf == null || buf.length == 0) {
            return EMPTY;
        }
        if (offset >= buf.length) {
            offset = buf.length - 1;
        }
        if (numBytes > buf.length - offset) {
            numBytes = buf.length - offset;
        }
        StringBuffer out = new StringBuffer();
        int i;
        int j;
        byte[] saveBuf= new byte[ ROW_BYTES + 2 ];
        char[] hexBuf = new char[ 4 ];
        char[] idxBuf = new char[ 8 ];
        int rows = numBytes >> 4;
        int residue = numBytes & 0x0000000F;
        for ( i = 0 ; i < rows ; i++ ) {
            int hexVal = (i * ROW_BYTES);
            idxBuf[0] = HEX[ ((hexVal >> 12) & 15) ];
            idxBuf[1] = HEX[ ((hexVal >> 8) & 15) ];
            idxBuf[2] = HEX[ ((hexVal >> 4) & 15) ];
            idxBuf[3] = HEX[ (hexVal & 15) ];

            String idxStr = new String( idxBuf, 0, 4 );
            out.append( idxStr + ": " );
        
            for ( j = 0 ; j < ROW_BYTES ; j++ ) {
                saveBuf[j] = buf[ offset + (i * ROW_BYTES) + j ];

                hexBuf[0] = HEX[ (saveBuf[j] >> 4) & 0x0F ];
                hexBuf[1] = HEX[ saveBuf[j] & 0x0F ];

                out.append( hexBuf[0] );
                out.append( hexBuf[1] );
                out.append( ' ' );

                if ( j == ROW_QTR1 || j == ROW_HALF || j == ROW_QTR2 ){
                    out.append( ' ' );
                }

                if ( saveBuf[j] < 0x20 || saveBuf[j] > 0x7E ){
                    saveBuf[j] = (byte) '.';
                }
            }

            String saveStr = new String( saveBuf, 0, j );
            out.append( " ; " + saveStr + "\n" );
        }
        
        if ( residue > 0 ) {
            int hexVal = (i * ROW_BYTES);
            idxBuf[0] = HEX[ ((hexVal >> 12) & 15) ];
            idxBuf[1] = HEX[ ((hexVal >> 8) & 15) ];
            idxBuf[2] = HEX[ ((hexVal >> 4) & 15) ];
            idxBuf[3] = HEX[ (hexVal & 15) ];

            String idxStr = new String( idxBuf, 0, 4 );
            out.append( idxStr + ": " );

            for ( j = 0 ; j < residue ; j++ ) {
                saveBuf[j] = buf[ offset + (i * ROW_BYTES) + j ];

                hexBuf[0] = HEX[ (saveBuf[j] >> 4) & 0x0F ];
                hexBuf[1] = HEX[ saveBuf[j] & 0x0F ];

                out.append( (char)hexBuf[0] );
                out.append( (char)hexBuf[1] );
                out.append( ' ' );

                if ( j == ROW_QTR1 || j == ROW_HALF || j == ROW_QTR2 ){
                    out.append( ' ' );
                }

                if ( saveBuf[j] < 0x20 || saveBuf[j] > 0x7E ){
                    saveBuf[j] = (byte) '.';
                }
            }
                
            for ( /*j INHERITED*/ ; j < ROW_BYTES ; j++ ) {
                saveBuf[j] = (byte) ' ';
                out.append( "   " );
                if ( j == ROW_QTR1 || j == ROW_HALF || j == ROW_QTR2 ){
                    out.append( " " );
                }
            }
            
            String saveStr = new String( saveBuf, 0, j );
            out.append( " ; " + saveStr + "\n" );
        }
        
        return out.toString();
    }

    /**
     * 测试
     * @param args 
     */
    public static void main( String[] args ) {
        byte[] data = new byte[1024 * 64];
        for ( int i = 0 ; i < data.length ; ++i ) {
            data[i] = (byte)i;
        }
        System.out.println(dumpHex( data, 0, data.length ));
    }
    

}
