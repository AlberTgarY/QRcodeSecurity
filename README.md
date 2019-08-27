# QRcodeSecurity 二维码加密商品防伪（DEMO）：
使用DES，RSA双重加密，输入明文信息后对明文进行DES加密生成密文信息A,将DES加密使用的密匙进行RSA加密生成RSA公钥密文信息B.
将A和B生成对应的二维码储存在相应的文件夹内，RSA公钥/私钥以及加密后的DES密匙储存在对应的txt文件内方便验证时读取(充当服务器).

# 验证时：
若输入正确二维码信息，则解密成功，此二维码信息证实，产品为正品.
若输入伪造二维码信息导致解密失败，则此二维码信息证伪，产品为赝品.

使用时请检查文件路径是否正确并在Verify.java文件内进行操作.


# 参考链接：
https://blog.csdn.net/wangnan537/article/details/50353174

https://www.cnblogs.com/gisblogs/p/5082344.html

https://www.cnblogs.com/caizhaokai/p/10944667.html

https://github.com/zxing/zxing/releases

2019-8-27 Albert 16:44
