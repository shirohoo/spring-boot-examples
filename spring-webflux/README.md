![Java_8](https://img.shields.io/badge/java-8-red?logo=java)
![Spring_Boot](https://img.shields.io/badge/Spring_Boot-v2.6.2-green.svg?logo=spring)
[![GitHub license](https://img.shields.io/github/license/TEAM-ARK/sprout-backend)](https://github.com/TEAM-ARK/sprout-backend)

<br />

- [๐ ์คํ๋ง ๋ถํธ ์ค์  ํ์ฉ ๋ง์คํฐ - ๊ทธ๋  ํดํค์คํธ ์ง์, ์ค๋ช์ด ์ฎ๊น](http://www.kyobobook.co.kr/product/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9791189909307)

![image](https://user-images.githubusercontent.com/71188307/148669388-aac14122-76af-4e6d-b6cc-810ed23718a8.png)

<br />

# ์คํ๋ง ๋ถํธ์ ์น ํ๋ญ์ค

---

์น ํ๋ญ์ค๋ ๋ฆฌ์กํฐ๋ธ ์คํธ๋ฆผ์ ์ด์ฉํ ๋น๋๊ธฐ ์ฒ๋ฆฌ๋ฅผ ์ง์ํ๋ค.

๋ฆฌ์กํฐ๋ธ ์คํธ๋ฆผ์ด๋ ๋ฐํ์(publisher)์ ๊ตฌ๋์(subscriber)๊ฐ์ ๊ณ์ฝ์ ์ ์ํ๋ ๋ช์ธ๋ก, ์ฌ๊ธฐ์ ๋ฐํ์๋ ์๋ฒ, ๊ตฌ๋์๋ ์๋ฒ์ ์์ฒญ์ ๋ณด๋ด๋ ๋ชจ๋  ํด๋ผ์ด์ธํธ๋ฅผ ์๋ฏธํ๋ค.

์๋ฅผ ๋ค์ด, ํด๋ผ์ด์ธํธ๊ฐ ์๋ฒ์ **xx์ ๋ํ ๋ฐ์ดํฐ 10๊ฐ๋ง ๋ณด๋ด์ค** ๋ผ๊ณ  ์๋ฒ์ ์์ฒญํ๋ฉด, ์๋ฒ๋ ์ ํํ 10๊ฐ์ ๋ฐ์ดํฐ๊ฐ ์ค๋น๋๋๋๋ก ๋๋ ค์ฃผ๋ ๊ฒ์ด๋ค.

์ด๋ฌํ ๊ณ์ฝ์ ๋ฆฌ์กํฐ๋ธ ์คํธ๋ฆผ์ด๋ผ ํ๋ค.

<br />

## Spring Data MongoDB

## Query Methods

---

![image](https://user-images.githubusercontent.com/71188307/148669353-e197403b-6216-4675-947b-e1320dafa459.png)
![image](https://user-images.githubusercontent.com/71188307/148669357-0e5985d1-edb5-4d38-8730-6ef337591e44.png)

<br />

## Return types

---

![image](https://user-images.githubusercontent.com/71188307/148669359-67c09bdc-2dea-4338-bc1e-286f7d02e98c.png)

<br />

## Trade-Off

---

![image](https://user-images.githubusercontent.com/71188307/148669371-8d363ac1-66f7-4b72-bd03-c85660270602.png)


## BlockHound

---

๋ธ๋กํน API ํธ์ถ์ ๊ฐ์งํ๋ ์๋ฐ ์์ด์ ํธ์ด๋ค.

์๋ฐ ์์ด์ ํธ๋ ์๋ฐ๊ฐ ์คํ๋๊ธฐ ์  ๋จผ์  ์คํ๋์ด(premain) ์ ํ๋ฆฌ์ผ์ด์์ ๋ฐ์ดํธ ์ฝ๋๋ฅผ ์กฐ์ํ๋ ๊ฐ์ฒด์ด๋ค.

<br />

```groovy
// build.gradle
dependencies{
    implementation'io.projectreactor.tools:blockhound:1.0.6.RELEASE'
}
```

<br />

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        BlockHound.install(); // ์ถ๊ฐ
        SpringApplication.run(Application.class, args);
    }
}

```

<br />

์ ํ๋ฆฌ์ผ์ด์์ ์คํํ๊ณ  `localhost:8080`์ผ๋ก ์ง์ํ๋ฉด ๋ธ๋กํ์ด๋๋ ๋ธ๋กํน API๊ฐ ํธ์ถ๋์์ ๊ฐ์งํ๊ณ  ์๋ ค์ค๋ค.

<br />

![image](https://user-images.githubusercontent.com/71188307/148671222-439b18fb-85b9-489c-99b3-a18a01e3a5cf.JPG)

<br />

์ด๋ฌํ ๋ธ๋กํน API๋ฅผ ๊ฐ์ํ  ์ ์๋ค๋ฉด ๋ธ๋กํ์ด๋๊ฐ ํด๋น ๋ธ๋กํน API๋ฅผ ๊ฐ์งํ์ง ์๋๋ก ํ์ฉ(allow)ํด์ฃผ๋ฉด ๋๋ค.

<br />

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        BlockHound.builder()
            .allowBlockingCallsInside(TemplateEngine.class.getCanonicalName(), "process")
            .allowBlockingCallsInside(FileInputStream.class.getCanonicalName(), "readBytes")
            .install();

        SpringApplication.run(Application.class, args);
    }
}
```

<br />
