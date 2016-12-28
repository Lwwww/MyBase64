import java.io.UnsupportedEncodingException;

/**
 * Created by 73995 on 2016/9/22.
 */
public class MyBase64 {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String nihao = "nihao123456";

		System.out.println(new String(MyBase64.encode(nihao.getBytes())));
	}

	public static byte[] encode(byte[] binaryByte) {
		//计算转换后byte数组长度
		int length = binaryByte.length / 3 * 4;
		if (binaryByte.length % 3 != 0) {
			length += 4;
		}

		byte[] encoded = new byte[length];
		int count = 0;

		for (int i = 0, tmp; i < binaryByte.length; i += 3) {
			//判断是否有第三第四个字节
			boolean d3 = false, d4 = false;
			tmp = 0;
			tmp |= (binaryByte[i] << 24);
			if (i + 1 < binaryByte.length) {
				tmp |= (binaryByte[i + 1] << 16 & 0xff0000);
				d3 = true;
			}
			if (i + 2 < binaryByte.length) {
				tmp |= (binaryByte[i + 2] << 8 & 0xff00);
				d4 = true;
			}
			encoded[count++] = (byte) (parse((tmp & 0xfc000000) >>> 26));
			encoded[count++] = (byte) (parse((tmp & 0x3f00000) >>> 20));
			encoded[count++] = d3 ? (byte) (parse((tmp & 0xfc000) >>> 14)) : 61;
			encoded[count++] = d4 ? (byte) (parse((tmp & 0x3f00) >>> 8)) : 61;
		}
		//填充=
		while (count < encoded.length) {
			encoded[count++] = 61;
		}
		return encoded;
	}

	private static int parse(int num) {
		if (0 <= num && num <= 25) {
			return num + 65;
		} else if (26 <= num && num <= 51) {
			return num + 71;
		} else if (52 <= num && num <= 61) {
			return num - 4;
		} else if (num == 62) {
			return 43;
		} else if (num == 63) {
			return 47;
		}
		return 0;
	}
}