package p.minn.common.utils;

public class DigestTest {

	public static void main(String args[]){
		// 得到8位盐
		byte[] salts = Digests.generateSalt(8);
		// 将8位byte数组装换为spring
		String salt = Encodes.encodeHex(salts);
		// 将spring数组转化为8位byte数组
		salts = Encodes.decodeHex(salt);

		// 原密码
		String password = "123456";
		// 对密码加盐进行1024次SHA1加密
		byte[] hashPassword = Digests.sha1(password.getBytes(), salts, 1024);
		// 将加密后的密码数组转换成字符串
		password = Encodes.encodeHex(hashPassword);
	}
}
