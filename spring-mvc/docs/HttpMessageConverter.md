# ๐ก HttpMessageConverter

---

![spring-mvc](https://user-images.githubusercontent.com/71188307/143034392-8d728cd9-edce-46fe-b98c-6c4fd829c0c4.jpg)

<br />

`HttpMessageConverter`๋ `HandlerMethodArgumentResolver`๊ฐ ์ฒ๋ฆฌํ์ง ๋ชปํ๋ ์ ํ์ ์์ฒญ์ ๋์  ์ฒ๋ฆฌํด์ค๋ค.

์ฌ๊ธฐ์ `HandlerMethodArgumentResolver`๊ฐ ์ฒ๋ฆฌํ์ง ๋ชปํ๋ ์ ํ์ ์์ฒญ์ด๋, ๋ฐ์ดํฐ๊ฐ HTTP ๋ฐ๋์ ๋ค์ด์๋ ๊ฒฝ์ฐ๋ฅผ ์๋ฏธํ๋ค. 

(์ด๋, ์์ฒญ์ ์ฒ๋ฆฌ ์ฃผ์ฒด๊ฐ Spring MVC์ด๋ฏ๋ก HTTP ์์ฒญ์์ ๊ฐ์ ํ๋ค.)

<br />

![image](https://user-images.githubusercontent.com/71188307/143870529-4f7da105-b0d5-4beb-a930-4605c82d671b.png)

- ์ด๋ฏธ์ง ์ถ์ฒ: https://developer.mozilla.org/ko/docs/Web/HTTP/Messages

<br />

HTTP ๋ฉ์์ง๋ ์ฒซ์ค์ ์์ฒญ์ ํต์ฌ์ ๋ณด๋ฅผ ํ์ํ๊ณ , ์ด์ด์ ๋๋ฒ์งธ ์ค๋ถํฐ `HTTP ํค๋`๋ผ๋ ์ด๋ฆ์ ๋ฉํ๋ฐ์ดํฐ๋ฅผ ์ญ ๋์ดํ๋ค. (์์ฒญ์ ๋ํ ์์ธ ์ค๋ช์ด๋ผ๊ณ  ์ดํดํ๋ฉด ๋๊ฒ ๋ค)

์ดํ ๊ณต๋ฐฑ๋ผ์ธ์ด ํ์ค ๋ค์ด๊ฐ๊ณ , ๋ค์๋ถํฐ๋ `HTTP ๋ฐ๋`๋ผ๋ ์ด๋ฆ์ ๋ณธ๊ฒฉ์ ์ธ ๋ฐ์ดํฐ๋ฅผ ๋ด๋ ๊ณต๊ฐ์ด ์กด์ฌํ๋ค.

๋ค๋ง `GET`๊ฐ์ ๋ฐฉ์์ HTTP ๋ฐ๋๋ฅผ ์ฌ์ฉํ์ง ์๊ณ , `URL`์ ์์ฒญ์ ํ์ํ ๋ฐ์ดํฐ๋ฅผ ๋์ดํ๋๋ฐ, ์ด๋ฅผ `์ฟผ๋ฆฌ์คํธ๋ง` ํน์ `์ฟผ๋ฆฌํ๋ผ๋ฏธํฐ`๋ผ๊ณ  ๋ถ๋ฅธ๋ค.

์ผ๋ฐ์ ์ผ๋ก `HandlerMethodArgumentResolver`๋ ์ด `์ฟผ๋ฆฌ์คํธ๋ง`์ ๋ถ์ํด ๊ฐ์ฒด๋ก ๋ณํํด์ฃผ๋ ์ญํ ์ ํ๋ฉฐ, `HttpMessageConverter`๋ `HTTP ๋ฐ๋`๋ฅผ ๋ถ์ํด ๊ฐ์ฒด๋ก ๋ณํํด์ฃผ๋ ์ญํ ์ ํ๋ค.

<br />

`HttpMessageConverter`๋ ๋ค์๊ณผ ๊ฐ์ ์ถ์๋ฉ์๋๋ค์ ํฌํจํ ์ธํฐํ์ด์ค๋ก ์ ์๋ผ์๋ค.

์ด๋ฆ์ด ๋งค์ฐ ์ง๊ด์ ์ด๋ผ ๋ฐ๋ก ์ค๋ช์ด ํ์ํ  ๊ฒ ๊ฐ์ง ์์ง๋ง, ํน์ ๋ชฐ๋ผ ์ด์ ๋ํ ์ค๋ช์ ์ฝ๋์ ์ฃผ์์ผ๋ก ์์ฑํ์๋ค.

<br />

```java
public interface HttpMessageConverter<T> {

    // HttpMessageConverter๊ฐ ์ง์ ๋ ํ์์ ์ฝ์ ์ ์๋์ง์ ์ฌ๋ถ๋ฅผ ํ๋จํ๋ค.
    // ์ฒซ๋ฒ์งธ ์ธ์๋ ์ฝ๊ณ ์ ํ๋ ํ์์ด๋ฉฐ, ๋๋ฒ์งธ ์ธ์๋ HTTP ํค๋์ Content-Type์ ์๋ฏธํ๋ค.
	boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);

    // HttpMessageConverter๊ฐ ์ง์ ๋ ํ์์ ์์ฑํ  ์ ์๋์ง์ ์ฌ๋ถ๋ฅผ ํ๋จํ๋ค.
    // ์ฒซ๋ฒ์งธ ์ธ์๋ ์์ฑํ๊ณ ์ ํ๋ ํ์์ด๋ฉฐ, ๋๋ฒ์งธ ์ธ์๋ HTTP ํค๋์ Accept๋ฅผ ์๋ฏธํ๋ค.
	boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);

    // HttpMessageConverter๊ฐ ์ง์ํ๋ ๋ฏธ๋์ดํ์์ ๋ชฉ๋ก์ ๋ฐํํ๋ค.
	List<MediaType> getSupportedMediaTypes();

    // ์ธ์๋ก ๋์ด์จ ํ์์ ๋ํด ์ง์(์ฝ๊ธฐ, ์ฐ๊ธฐ)ํ๋ ๋ชจ๋  ๋ฏธ๋์ด ํ์์ ๋ฐํํ๋ค.
	default List<MediaType> getSupportedMediaTypes(Class<?> clazz) {
		return (canRead(clazz, null) || canWrite(clazz, null) ?
				getSupportedMediaTypes() : Collections.emptyList());
	}

    // HTTP ๋ฉ์์ง๋ฅผ ์ฝ๊ณ  ์ฒซ๋ฒ์งธ ์ธ์๋ก ๋์ด์จ ํ์์ ์ธ์คํด์ค๋ฅผ ์์ฑํ ํ ๋ฐ์ดํฐ๋ฅผ ๋ฐ์ธ๋ฉํด ๋ฐํํ๋ค
    // ๋๋ฒ์งธ ์ธ์๋ ํด๋ผ์ด์ธํธ๊ฐ ๋ณด๋ธ ์์ฒญ์ด๋ค.
	T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException;

    // ์ฒซ๋ฒ์งธ ์ธ์๋ก ๋์ด์จ ํ์์ ์ฝ์ด ๋๋ฒ์งธ ์ธ์๋ก ๋์ด์จ Content-Type์ผ๋ก ํ์ฑํ๋ค.
    // ์ดํ ์ธ๋ฒ์งธ ์ธ์๋ก ๋์ด์จ, ํด๋ผ์ด์ธํธ์๊ฒ ๋ณด๋ผ ์๋ต์ ์์ฑํ๋ค. 
	void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException;

}
```

<br />

`HttpMessageConverter`์๋ 10๊ฐ์ ๊ตฌํ์ฒด๊ฐ ์กด์ฌํ๋ค.

์ฝ๊ฐ ์ฒจ์ธํ์๋ฉด, `ํด๋ผ์ด์ธํธ์ ์์ฒญ์ ๋ถ์`ํ ๋๋ HTTP ํค๋์ `Content-Type`์ ์ฐธ๊ณ ํ๋ฉฐ, `ํด๋ผ์ด์ธํธ์๊ฒ ์๋ต์ ๋ฐํ`ํ ๋๋ HTTP ํค๋์ `Accept`๋ฅผ ์ฐธ๊ณ ํ๋ค.

<br />

> ๐ก Content-Type: ํด๋ผ์ด์ธํธ๊ฐ ์๋ฒ์ ๋ณด๋ด๋ ๋ฐ์ดํฐ์ ํ์์ ๋ช์ํ ํ์ค ํค๋
> 
> ๐ก Accept: ํด๋ผ์ด์ธํธ๊ฐ ์๋ฒ์๊ฒ์ ์๋ต๋ฐ๊ธธ ์ํ๋ ๋ฐ์ดํฐ์ ํ์์ ๋ช์ํ ํ์ค ํค๋

<br />

์ฆ, `Content-Type`๊ณผ `Accept`์ ๋ฐ๋ผ 10๊ฐ์ `HttpMessageConverter`์ค ์ด๋ค๊ฒ์ด ์ฌ์ฉ๋ ์ง๊ฐ ๊ฒฐ์ ๋๋ค.

<br />

![image](https://user-images.githubusercontent.com/71188307/143871615-810177aa-6124-4161-8c1a-e6defe35d28f.png)

<br />

์ด์ค ๊ฒฝํ์ ๊ฐ์ฅ ๋ง์ด ์ฌ์ฉ๋๋ ๊ตฌํ์ฒด๋ `MappingJackson2HttpMessageConverter`์ธ๋ฐ, ์ด๋์์ ์ด๋ฆ ๊ทธ๋๋ก `application/json` ํ์์ ๋ฉ์์ง๋ฅผ ํ์ฑํ๋ ์ฑ์์ ๊ฐ๋๋ค. (`Jackson`์ `json`์ ์ฒ๋ฆฌํ๋ ํ์ค ๋ผ์ด๋ธ๋ฌ๋ฆฌ์ ์ด๋ฆ์ด๋ค. ex) `ObjectMapper`)

ํ๋ฐ ์ ์ด๋ฏธ์ง ์ ๋ฆฌ์คํธ 7,8๋ฒ ์ธ๋ฑ์ค์ ์์นํ `MappingJackson2HttpMessageConverter`๊ฐ ๋๊ฐ์ธ๋ฐ, ์ ๋๊ฐ์ธ์ง ์ดํด๋ณด๋ฉด `์ธ์ฝ๋ฉ`์ด ๋ค๋ฅด๋ค.

ํ๋๋ `ISO-8859-1`์ด๋ฉฐ, ํ๋๋ `UTF-8`์ธ๋ฐ ๊ด๋ จ ํ์คํ ๋ฆฌ๋ฅผ ์ฐพ์๋ณด๋, ์์  ์คํ๋ง์ `ISO-8859-1`๋ง์ ์ง์ํ๊ณ  ์์๋ ๋ฏ ํ๋ค.

์ดํ ์ ๋์ฝ๋๊ฐ ๋์ธ๋ก ์ฌ์ฉ๋จ์ ๋ฐ๋ผ ์ถ๊ฐ๋๊ฒ์ผ๋ก ๋ณด์ธ๋ค.

๊ด๋ จ ์ปค๋ฐ์ ๋ค์ ๋งํฌ๋ฅผ ์ฐธ๊ณ ๋ฐ๋๋ค.

<br />

> ๐ก https://github.com/spring-projects/spring-framework/commit/c38542739734c15e84a28ecc5f575127f25d310a

<br />

10๊ฐ์ `HttpMessageConverter`๋ ArrayList๋ก ๊ด๋ฆฌ๋๊ณ  ์์ผ๋ฉฐ, ์ด ๋์๋ค์๊ฒ ์ฒ๋ฆฌ๋ฅผ ์์ํ๋ `HandlerMethodArgumentResolver`์ ๊ตฌํ์ฒด๋ ๋ค์ ๋ ์ข๋ฅ๊ฐ ์กด์ฌํ๋ ๊ฒ ๊ฐ๋ค. (~~๋ ์์ ์๋ ์๋ค.~~)

- RequestResponseBodyMethodProcessor
- HttpEntityMethodProcessor

<br />

## RequestResponseBodyMethodProcessor

---

`RequestResponseBodyMethodProcessor`๊ฐ ์ฌ์ฉ๋๋ ๊ฒฝ์ฐ๋ ๋ค์๊ณผ ๊ฐ๋ค.

<br />

```java
public class RequestResponseBodyMethodProcessor extends AbstractMessageConverterMethodProcessor {

    // ํด๋ผ์ด์ธํธ์ ์์ฒญ์ ๋ถ์ํ ๋ ์ฌ์ฉ๋ ์ง ์ฌ๋ถ
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class); // @RequestBody๊ฐ ๋ฌ๋ ค์๋ ๊ฒฝ์ฐ
    }

    // ํด๋ผ์ด์ธํธ์๊ฒ ์๋ตํ  ๋ ์ฌ์ฉ๋ ์ง ์ฌ๋ถ
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) ||
            returnType.hasMethodAnnotation(ResponseBody.class)); // @ResponseBody๊ฐ ๋ฌ๋ ค์๋ ๊ฒฝ์ฐ
    }

}
```

<br />

์ฝ๋๋ฅผ ๋ณด๋ฉด ์ธ์์ `@RequestBody`๊ฐ ์กด์ฌํ๋ ๊ฒฝ์ฐ์ ๋ฆฌํดํ์์ด๋ ๋ฉ์๋์ `@ResponseBody`๊ฐ ์กด์ฌํ๋ ๊ฒฝ์ฐ ์ด ๊ตฌํ์ฒด๊ฐ ์ฌ์ฉ๋ ๊ฒ์์ ์ ์ ์๋ค.

์ฌ๊ธฐ์ `@ResponseBody`๊ฐ ์ฌ์ฉ๋๋ ๊ฒฝ์ฐ๊ฐ ์๊ฐ๋ณด๋ค ๊ต์ฅํ ๋ง์๋ฐ, ํน์๋ `HTTP API`๋ผ๊ณ  ๋งํ๊ณ , ํน์๋ `REST API`๋ผ๊ณ  ๋งํ๋, ์ฐ๋ฆฌ๊ฐ ์์ฃผ ์ฌ์ฉํ๋ `@RestController`๋ฅผ ์ฌ์ฉํ๊ฒ ๋๋ฉด ๋ค์๊ณผ ๊ฐ์ ์ฝ๋๊ฐ ์จ๊ฒจ์ ธ์๋ค.

<br />

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody // <- ์๋์
public @interface RestController {
}
```

<br />

์ฆ, `RequestResponseBodyMethodProcessor`๋ ์๊ฐ๋ณด๋ค ์์ฃผ ๋ง์ด ์ฌ์ฉ๋๋ ๊ตฌํ์ฒด์ด๋ค.

์ ์ฒด์ ์ธ ์ธ๋ถ ๋์์ ์ฌ์ค `HandlerMethodArgumentResolver`์ ํฐ ์ฐจ์ด๊ฐ ์๊ณ  ์ด์ ๋ํ ๋ด์ฉ์ ์ด์  ๋ฌธ์์ ์์ฑํ์์ผ๋ ์๋ตํ๊ฒ ๋ค.

<br />

## HttpEntityMethodProcessor

---

`HttpEntityMethodProcessor`๊ฐ ์ฌ์ฉ๋๋ ๊ฒฝ์ฐ๋ ๋ค์๊ณผ ๊ฐ๋ค.

<br />

```java
public class HttpEntityMethodProcessor extends AbstractMessageConverterMethodProcessor {
    
    // ํด๋ผ์ด์ธํธ์ ์์ฒญ์ ๋ถ์ํ ๋ ์ฌ์ฉ๋ ์ง ์ฌ๋ถ
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (HttpEntity.class == parameter.getParameterType() ||
            RequestEntity.class == parameter.getParameterType());
    }

    // ํด๋ผ์ด์ธํธ์๊ฒ ์๋ตํ  ๋ ์ฌ์ฉ๋ ์ง ์ฌ๋ถ
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (HttpEntity.class.isAssignableFrom(returnType.getParameterType()) &&
            !RequestEntity.class.isAssignableFrom(returnType.getParameterType()));
    }

}
```

<br />

์ฝ๋๋ฅผ ๋ณด๋ฉด ์์ฒญ์ ์ฒ๋ฆฌํ๋ ๋ฉ์๋์ ์ธ์๊ฐ `HttpEntity` ํ์์ด๊ฑฐ๋ `RequestEntity` ํ์์ธ ๊ฒฝ์ฐ ๋์ํ๋ค.

ํด๋ผ์ด์ธํธ์๊ฒ ์๋ต์ ๋ฐํํ๋ ๊ฒฝ์ฐ๋ `HttpEntity` ํ์์ด๋ฉด์ `RequestEntity`๊ฐ ์๋ ๊ฒฝ์ฐ๋ฅผ ์๋ฏธํ๋ค. 

์ฆ, `HttpEntity`์ด๊ฑฐ๋ `ResponseEntity`์ธ ๊ฒฝ์ฐ์ด๋ค.

<br />

์ฌ๊ธฐ์ `HttpEntity`๋ ์คํ๋ง ํ๋ ์์ํฌ์์ ์ ๊ณตํ๋ ํด๋์ค๋ก, HTTP ๋ฉ์์ง ์์ฒด๋ฅผ ์๋ฐ ๊ฐ์ฒด๋ก ๋ชจ๋ธ๋งํ ํด๋์ค์ด๋ฉฐ, ํฌ๊ฒ ์ด๋ฅผ ์์๋ฐ์ `RequestEntity`์ `ResponseEntity`๋ก ๋๋๋ค.

<br />

![image](https://user-images.githubusercontent.com/71188307/143875877-17d41110-e21d-49dd-b7f4-5c3ec6690dc7.png)

<br />

์ค์ ๋ก ๋ค์๊ณผ ๊ฐ๋ค. (`HttpMessageConverter`๋ฅผ ์ฌ์ฉํ๊ธฐ ์ํด HTTP ๋ฐ๋์ ๋ฐ์ดํฐ๋ฅผ ๋ด๋ POST ๋ฐฉ์์ ์ฝ๋๋ฅผ ์์๋ก ์ถ๊ฐํฉ๋๋ค.)

<br />

```java
@Slf4j
@RestController
public class PostApiController {
    
    @PostMapping("/v1/hello")
    public String helloV1(@RequestBody Cat cat) {
        log.info("cat={}", cat);
        return "ok";
    }

}

```

<br />

์ด๋ฐ ์ฝ๋๋ ์ ๋์ํ์ง๋ง **_(์ธ์์ `@RequestBody`๊ฐ ์ ์ธ๋ผ์์ผ๋ `RequestResponseBodyMethodProcessor`๊ฐ ์ฌ์ฉ๋  ๊ฒ์์ ์ ์ถํ  ์ ์๋ค.)_**

<br />

```java
@Slf4j
@RestController
public class PostApiController {
    
    @PostMapping("/v1/hello")
    public String helloV1(RequestEntity<Cat> cat) { // RequestEntity<T>์ธ ๊ฒฝ์ฐ
        log.info("cat={}", cat);
        return "ok";
    }

    @PostMapping("/v2/hello")
    public String helloV2(HttpEntity<Cat> cat) { // HttpEntity<T>์ธ ๊ฒฝ์ฐ
        log.info("cat={}", cat);
        return "ok";
    }

}

```

<br />

์ด๋ ๊ฒ ์์ฑํด๋ ์์ฃผ ์ ๋์ํ๋ค.

์ฌ๊ธฐ์๋ `HttpEntityMethodProcessor` ๊ฐ ์ฌ์ฉ๋  ๊ฒ์ ์ ์ถํ  ์ ์๋ค.

<br />

# ์ฃผ์์ฌํญ

---

- `@RequestBody`๋ฅผ ์ฌ์ฉํ ๋๋ `@RequestParam`์ฒ๋ผ ๊ฐ ํค๋ณ๋ก ํ๋ํ๋ ๋ผ์ค๊ธฐ ์ํด `Map`์ ์ฌ์ฉํด์ผ ํ๋ค.
  - ์ด๊ฒ ์ซ๋ค๋ฉด ๋ณ๋์ ์ปจ๋ฒํฐ๋ฅผ ๊ตฌํํ์ฌ ์ง์  ๋ฑ๋กํด์ผ๋ง ํ๋ค ! 
  
![image](https://user-images.githubusercontent.com/71188307/143877917-ed7b5054-b7c2-47fd-bc09-fa2e435d07ab.png)

<br />

- `@RequestBody`๋ฅผ ์ฌ์ฉํ ๋๋ ๊ธฐ๋ณธ์์ฑ์๊ฐ ๋ฐ๋์ ํ์ํ๋ฉฐ, ์ ๊ทผ์ ํ์๋ `private`์ด์ฌ๋ ๋ฌด๋ฐฉํ๋ค.
  - ๊ตฌํ์ฒด๋ง๋ค ์กฐ๊ธ์ฉ ๋ค๋ฅด๊ฒ ์ง๋ง ์ผ๋ฐ์ ์ผ๋ก ๋ฆฌํ๋ ์์ ํตํด ๋์ํ๊ธฐ ๋๋ฌธ์ด๋ฉฐ, ํนํ ์ฐ๋ฆฌ๊ฐ ์์ฃผ ์ฌ์ฉํ๋ `@RestController`์์๋ `ObjectMapper`๊ฐ ์ฌ์ฉ๋๋ค.
  - ์ด๋ง์ธ์ฆ์จ, `์์ ์(Setter)`๊ฐ ํ์ํ์ง ์๋ค๋ ์๋ฏธ์ด๋ ํ๋ค.

<br />

- `POST` ๋ฐฉ์์ฒ๋ผ `HTTP ๋ฐ๋`์ ๋ฐ์ดํฐ๋ฅผ ๋ด๋ ํ์์ ํต์ ์ ํ๋๋ผ๋ HTTP ํ๋กํ ์ฝ์ ํน์ฑ์ ์ฌ์ ํ `์ฟผ๋ฆฌ์คํธ๋ง`์ ์ฌ์ฉํ  ์ ์๋ค.
  - ์ฆ, `@PostMapping`์์๋ `@RequestParam`์ ์ฌ์ฉํ  ์ ์๋ค.
  - ๋จ, ์ด ๊ฒฝ์ฐ ์ผ๋ฐ์ ์ผ๋ก `SSR(์๋ฒ์ฌ์ด๋๋ ๋๋ง)`์ ํ์ง ์์ผ๋ฏ๋ก `@ModelAttribute`๋ฅผ ์ฌ์ฉํด์ผํ  ์ด์ ๊ฐ ์์ ์กด์ฌํ์ง ์์ง๋ง, ์ผ๋ถ๋ฌ ํ์คํธ ํด๋ณธ๊ฒฐ๊ณผ ์ญ์ ๋น์ ์์ ์ผ๋ก ๋์ํจ์ ํ์ธํ๋ค.

<br />
