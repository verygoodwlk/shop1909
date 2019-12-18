package com.qf.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayUtil {

    private static AlipayClient alipayClient;

    public static final String ALIPAY_PUBLICK_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

    static{
        alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016073000127352",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCavh3meHFv7HrlFZX+floi1KtW43K+fI0lUEYSV94RmF/7H2rdq8TiQaSLWfLdd3j6mGGnBRIkL7f9T53QY+Yvgvahj18zvkCN9FDKXUyHpezNZJa5j5Uw3XnC9FWIXKiQ/FB4ASbr1kKPdUHYXeRfg7QQ/Hn6sNLdOTCfSAzerCiTFc3T8dDe3cC7guBmg4gvL/2UlkOQ6tkEkt8+HXjU92Q+zCkLYkt726OobWav1wlrvQHVtRVgMsCi5+OzGmqeDU8eeWCDPatxFXY1/IpfovEwUlxyMjigaRriOBq3E+6laJjK214U0t7w0mfTCUU6qjNKjSEj1hXN3zvuOalNAgMBAAECggEAPAD+zTDlI/Z6TTzKlg3e2+TFsAKwqubXQKHZGiHSbdY9Wb58DQsfZHD54kN15qT+V4YhT8eqmjeay/JEittCFihdhJZ43zKmC6WyclYtZ+hSY+7ed4QS4Qjwv0VJqX/sqZP9jBs+QXMrCj/40XLPLK6Ac5nDnw9UJQ6ZKRqiSvGyfzDZSTOMKPESbkv7GUJg5R4FWTv9minEPuDUBShqgVgn2HJJNJrpsz9FbFx0i2i/RRU7E0YKKQzzZjUFZWJob9o3mmIygdC6vH9ytSTOlL6ELQ7SMh99EZkhu9JWN84B14g7id93iFJobp/9PlGy6WTXvKNuRMpgEYN6hKC4oQKBgQDTsGD0kL0Po2wJ8d280YBuylW0pW0FO6a9T68qiEXGZHTQyyDIQ7qWVNrFW5192kih6w8/MT4nANxzImUqo8ckAMoAlf9dHbG7lgZkY1aEbxE7h9B1X8PImD602byOgHCg1OfOncx4tPc3aH8P7KHy7mTIA67Z+XozSOk68p685QKBgQC7IjJe7axGNWr4lalGduhNSb7Zjt/Ww9cOvEM5grHQTM14nqwYxmegYQcL+jXBICDJa2sfOUKlFg+NKltrdX5aL0Xe9k0EAtK37MQwywxAsYHvZ4CrzLH1fGHuTx4s3ZM4UnOaS5IuUcQDoo2Mw1kdiqjLLClY5x8P3Jh6+Q3cSQKBgQCrtrIfEF1cFxsnQfu1E1gUBqrY+ythCE8r1zxmyt2WnZwoVe1EpbPwB+riFnu9P9iVB2B5yQ76gndQKYFYwjtiWAWDziiztseaO4r52Z6vlIys9nFBFKpyyBRtjQWnrkYuICXc8yGOdHTojCotl6ySq5AystId3IGkoisWdwIOhQKBgD46O+fHPw9FuiGZmuWcqtjwme88jICdNQloXy5joH902qqukOnYKCNzwGUiOffTmDOCbee/IcNF17wIhsfwFSkmKSTgYHHiAnI44HCdRKDKpjuW/nmiotbQGrylQJKIx+jluh1n3d9bP0Q6LLnPR/YD4udU8xbmHDUpFOdnpE1JAoGATTog2QyMFqDgzm2BTanxCUer0764fbuG8jd5f6U2rtU19lStKRMV3iZwvyJcOZ4l+o7l+3XBIiAR3Bslebz0NCip5D5lnIbt1l7js9y4IXPtwFvQPxWghkH7GbAHAxQmw78iwvH/Sy5W5pKyEYkBEQIWIxsRZcnq+sJiW8foCao=",
                "json",
                "UTF-8",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB",
                "RSA"); //获得初始化的AlipayClient
    }

    public static AlipayClient getAlipayClient(){
       return alipayClient;
    }
}
