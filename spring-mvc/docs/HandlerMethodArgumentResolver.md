# ๐ก HandlerMethodArgumentResolver

---

![spring-mvc](https://user-images.githubusercontent.com/71188307/143034392-8d728cd9-edce-46fe-b98c-6c4fd829c0c4.jpg)

<br />

์คํ๋ง MVC ํ๋ก์ ํธ์๋ ์ฌ์ฉ์์ HTTP ์์ฒญ์ ํ์ฑํ๊ณ  ์ฒ๋ฆฌํ ํ ์ปจํธ๋กค๋ฌ์ ๊ฐ์ ๋๊ฒจ์ฃผ๋ `HandlerMethodArgumentResolver` ์ธํฐํ์ด์ค๊ฐ ์์ผ๋ฉฐ **(์ฌ๊ธฐ์ ๋งํ๋ ํธ๋ค๋ฌ๋ ์ฐ๋ฆฌ๊ฐ ํํ ์ด์ผ๊ธฐํ๋ ์ปจํธ๋กค๋ฌ์ด๋ค)**, `HandlerMethodArgumentResolver`์ ์ฝํฌ๋ฆฌํธ ํด๋์ค๋ 20์ข์ด ๋๊ฒ ์กด์ฌํ๋ค.

<br />

`HandlerMethodArgumentResolver`๋ ๋ฐ์ดํฐ๊ฐ HTTP ํค๋์ ์กด์ฌํ๋ ๊ฒฝ์ฐ ์์์ ๋ฐ์ดํฐ๋ฅผ ํ์ฑํ๊ณ  ๊ฐ์ฒด์ ๋ฐ์ธ๋ฉ ํ์ง๋ง, ๋ง์ฝ ํด๋ผ์ด์ธํธ๊ฐ `POST` ๋ฑ์ ๋ฐฉ์์ ํตํด ์์ฒญ์ ๋ณด๋ด์ด ๋ฐ์ดํฐ๊ฐ `HTTP ๋ฐ๋`์ ์กด์ฌํ๋ ๊ฒฝ์ฐ์  `MessageConverter`์๊ฒ ์ฒ๋ฆฌ๋ฅผ ์์ํ๋ค. (์๋ต๋ ๋ง์ฐฌ๊ฐ์ง๋ค.)

์ ๊ฒฝ์ฐ์๋ `HTTP ๋ฐ๋`์ ๋ฐ์ดํฐ๋ฅผ ๋ฐ๊ฒ ๋ค๋ ์๋ฏธ์ `@RequestBody`๋ฅผ ์ ์ธํด์ฃผ์ด์ผ ํ๋ค. 

๋ง์ฐฌ๊ฐ์ง๋ก ์๋ต์๋ `@ResponseBody`๋ฅผ ์ ์ธํ๋ฉฐ `@RestController`๋ฅผ ์ฌ์ฉํ๋ค๋ฉด `@ResponseBody`๋ฅผ ์๋ตํ  ์ ์๋ค.

<br />

๋ฌด์จ ๋ง์ธ์ง ์ ๋ชจ๋ฅด๊ฒ ๋ค๋ฉด ์ญ์ ์ฝ๋๋ฅผ ๋ณด์ !

`HandlerMethodArgumentResolver`์ ๋ํ ํฌ์คํ์ด๋ **์๋จ ์ด๋ฏธ์ง์ 5๋ฒ๋ถํฐ ๋ณด๋ฉด ๋๊ฒ ๋ค.**

๋ํ ์ด ํฌ์คํ์์๋ ๊ฐ์ฅ ๋ง์ด ์ฌ์ฉํ๋ `@ModelAttribute`์ `@RequestParam`์ ๋ํด์๋ง ๋ค๋ฃฐ ๊ฒ์ด๋ค.

<br />

```java
@Slf4j
@RestController
public class HelloApiController {

    // ์ฌ์ฉ์์ ์์ฒญ์ ํ์ฑํด helloV1()์ ์ ์ธ๋ Person์ ๋ง๋ค๊ณ  ๋ฐ์ดํฐ๋ฅผ ๋ฐ์ธ๋ฉํด์ค๋ค
    @GetMapping("/v1/hello")  
    public Person helloV1(Person person) { // @ModelAttribute๊ฐ ์๋ ๊ฒฝ์ฐ
        log.info("person={}", person);
        return person;
    }

    @GetMapping("/v2/hello")
    public Person helloV2(@ModelAttribute Person person) { // @ModelAttribute๊ฐ ์๋ ๊ฒฝ์ฐ
        log.info("person={}", person);
        return person;
    }

    @GetMapping("/v3/hello")
    public String helloV3(String name, int age) { // @RequestParam์ด ์๋ ๊ฒฝ์ฐ
        log.info("name={}, age={}", name, age);
        return "ok";
    }

    @GetMapping("/v4/hello")
    public String helloV4(@RequestParam String name, @RequestParam int age) { // @RequestParam์ด ์๋ ๊ฒฝ์ฐ
        log.info("name={}, age={}", name, age);
        return "ok";
    }

}

@Getter
@Setter
@ToString
public class Person {

    private String name;
    private int age;

}
```

<br />

์๋ฒ๋ฅผ ๊ธฐ๋ํ๊ณ  ๋ธ๋ผ์ฐ์ ์ `localhost:8080/v1/hello?name=siro&age=11`์ ์๋ ฅํ๋ฉด ๋ฐ์ดํฐ๊ฐ ์๋ฒ๋ก ์ ์ก๋๊ณ (GET), ํธ๋ค๋ฌ ๋งคํ์ ํตํด ๊ฒฐ๊ตญ ์ ์ปจํธ๋กค๋ฌ ์ฝ๋์ ๋๋ฌํ  ๊ฒ์ด๋ค.

์ด๋ `Person`์ด๋ผ๋ ๊ฐ์ฒด๋ฅผ ๋ง๋ค๊ณ  ์ด๊ณณ์ ํธ๋ค๋ฌ ์ด๋ํฐ์๊ฒ ์ ๋ฌ๋ฐ์ ๋ฐ์ดํฐ๋ค์ ๋ฐ์ธ๋ฉํ ํ ์ปจํธ๋กค๋ฌ๋ก ๋๊ฒจ์ฃผ๋ ์ญํ ์ `HandlerMethodArgumentResolver`๊ฐ ํ๋ค.

๊ทธ๋ฌ๋ฉด ๊ฐ๋ฐ์๋ ๊ทธ๋ฅ ์ฟผ๋ฆฌ์คํธ๋ง์ ์ ๋ฌ๋ฐ์ `Person` ํด๋์ค๋ฅผ ์์ฑํด์ ์ ์ธํ๊ฑฐ๋ ํน์, ์ค์นผ๋ผํ์์ธ `String`๊ณผ `int`๋ง ์ ์ธํ๋ฉด ํ๋ฉด ๋๋ค. ๊ต์ฅํ ํธ๋ฆฌํ๋ค.

ํ์คํธ๋ฅผ ํ๊ธฐ์ ์์ ๋งค๋ฒ ์๋ฒ๋ฅผ ๊ป๋ค์ผฐ๋คํ๋ ๋ธ๊ฐ๋ค๋ฅผ ํ  ์๋ ์์ผ๋ ๊ฐ๋จํ ํ์คํธ ์ฝ๋๋ฅผ ์์ฑํ๋ค.

<br />

```java
// file: 'HelloApiControllerTest.class'
@WebMvcTest(HelloApiController.class)
class HelloApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("@ModelAttribute๊ฐ ์กด์ฌํ์ง ์๋ ๊ฒฝ์ฐ")
    void helloV1() throws Exception {
        performGet("v1");
    }

    @Test
    @DisplayName("@ModelAttribute๊ฐ ์กด์ฌํ๋ ๊ฒฝ์ฐ")
    void helloV2() throws Exception {
        performGet("v2");
    }

    @Test
    @DisplayName("@RequestParam์ด ์กด์ฌํ๋ ๊ฒฝ์ฐ")
    void helloV3() throws Exception {
        performGet("v3");
    }

    @Test
    @DisplayName("@RequestParam์ด ์กด์ฌํ์ง ์๋ ๊ฒฝ์ฐ")
    void helloV4() throws Exception {
        performGet("v4");
    }

    private void performGet(String version) throws Exception {
        mvc.perform(get("/" + version + "/hello?name=siro&age=11"))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
```

<br />

# โ ๊ตฌ์กฐ

---

๋ฌํํ๊ฒ ๋ดค์ ๋,

<br />

1. ์ฌ์ฉ์์ ์์ฒญ์ ๋ฐ์ ๊ด๋ฆฌํ๋ `DispatcherServlet` <u>(Dispatcher๋ ๊ด์ ํ์์ ์์ ๋ชจ๋ํฐ ์ฌ๋ฌ๊ฐ ๋๊ณ  ํค๋์ ๋ ์ํ๋ก ~ํ์ธ์. ~ํ์ธ์. ํ๋ ์ฌ๋๋ค์ ์ฐ์ํ๋ฉด ๋๋ค.)</u>
2. ์ฌ์ฉ์์ ์์ฒญ์ ์ฒ๋ฆฌํ  ํธ๋ค๋ฌ(=์ปจํธ๋กค๋ฌ)๋ฅผ ์ฐพ์์ฃผ๋ `HandlerMapping`
3. ์ฌ์ฉ์์ ์์ฒญ์ ์ฒ๋ฆฌํ  ํธ๋ค๋ฌ๋ฅผ `DispatcherServlet`์ ์ฐ๊ฒฐํด์ฃผ๋ `HandlerAdapter`
4. `HandlerAdapter`์ ์์ฒญ(๋ฉ์์ง)๋ฅผ ๋ฐ์ ์์ฒญ์ ํ์ฑํด ํธ๋ค๋ฌ์ ๋์ด๊ฐ ๋งค๊ฐ๋ณ์๋ก ๋ง๋ค์ด์ฃผ๋ `HandlerMethodArgumentResolver`
5. `HandlerMethodArgumentResolver`๊ฐ ์ฒ๋ฆฌํ์ง ๋ชปํ๋ ๊ฒฝ์ฐ(=๋ฐ์ดํฐ๊ฐ HTTP ๋ฐ๋์ ๋ค์ด์๋ ๊ฒฝ์ฐ), ์ด๋ฅผ ๋์  ์ฒ๋ฆฌํด์ค `MessageConverter`

<br />

๊ฐ ์๋ค, ๋ฌผ๋ก  ํจ์ฌ ๋ ๋ง์ ํด๋์ค๊ฐ ์กด์ฌํ์ง๋ง ๋ค ๋ณด๊ธฐ์๋ ๋๋ฌด๋๋ฌด ๋ฐฉ๋ํ๋ฏ๋ก ์ผ๋จ ์ด์ ๋๋ง ๋ณด์.

`HandlerAdapter`์ ์ฝํฌ๋ฆฌํธ ํด๋์ค์ค์๋ `@RequestMapping`์ด ๋ฌ๋ ค์๋ ์ปจํธ๋กค๋ฌ๋ค์ ์ฒ๋ฆฌํ๋ `RequestMappingHandlerAdapter`๊ฐ ์กด์ฌํ๋ฉฐ, ์ด๋์์ด ๊ฐ์ฅ ๋์ ์ฐ์ ์์๋ฅผ ๊ฐ๊ณ  ํธ์ถ๋๋ค.

`RequestMappingHandlerAdapter`๋ ๋งค๊ฐ๋ณ์ ์์ฑ์ `ModelFactory`์ ์์กดํ๋ฉฐ, `ModelFactory`๋ `HandlerMethod`์ ์ฝํฌ๋ฆฌํธ ํด๋์ค์ธ `InvocableHandlerMethod`์ ์์กดํ๋ค.

๊ทธ๋ฆฌ๊ณ  `InvocableHandlerMethod`๋ ๋ด๋ถ ํ๋๋ก `HandlerMethodArgumentResolverComposite`๋ฅผ ๊ฐ๋๋ฐ, ์ด๋ฆ์์ ์ด๋์์ด ํ๋ ์ญํ ์ ์์ฃผ ๊ทน๋ชํ๊ฒ ๋ํ๋ด๊ณ  ์๋ค.(์ฌ๊ธฐ์ `์ดํํฐ๋ธ ์๋ฐ`์ <u>์์๋ณด๋ค๋ ์ปดํฌ์ง์์ ํ์ฉํ๋ผ</u>๋ผ๋ ๋ฌธ๊ตฌ๊ฐ ๋ ์ฌ๋๋ค.)

`HandlerMethodArgumentResolverComposite`๋ ๋ด๋ถ์ ์ผ๋ก `HandlerMethodArgumentResolver`์ ๋ชจ๋  ์ฝํฌ๋ฆฌํธ ํด๋์ค๋ฅผ `ArrayList`๋ก ๊ฐ๊ณ  ์์ผ๋ฉฐ(27๊ฐ), ์์ฒญ์ ๋ฐ์ผ๋ฉด ๋ฃจํ๋ฅผ ๋๋ฉฐ ์๋ง์ `HandlerMethodArgumentResolver`๋ฅผ ์ฐพ๊ณ  ์ฒ๋ฆฌ๋ฅผ ์์ํ๋ค.(=์ปค๋งจ๋ ํจํด)

<br />

# โจ ModelAttributeMethodProcessor

---

๊ทธ์ค `ModelAttributeMethodProcessor`๋ `@ModelAttribute`๋ฅผ ์ฒ๋ฆฌํด์ฃผ๋ `HandlerMethodArgumentResolver`์ ์ฝํฌ๋ฆฌํธ ํด๋์ค ์ค ํ๋์ด๋ค.

์ฝ๋์ ์ดํดํ ๋ด์ฉ์ ์ฃผ์์ผ๋ก ๋ฌ์๋ค.

<br />

```java
// file: 'InvocableHandlerMethod.class'
public class InvocableHandlerMethod extends HandlerMethod {
    protected Object[] getMethodArgumentValues(NativeWebRequest request, @Nullable ModelAndViewContainer mavContainer,
        Object... providedArgs) throws Exception {

        MethodParameter[] parameters = getMethodParameters(); // ์ปจํธ๋กค๋ฌ์ ๋ฉ์๋์ ์ ์ธ๋ ๋งค๊ฐ๋ณ์์ ๊ฐ์๋ฅผ ์๋ฏธํ๋ค. ์ฌ๊ธฐ์  1๊ฐ(Person)๊ฐ ๋๊ฒ ๋ค
        if (ObjectUtils.isEmpty(parameters)) { // ์ปจํธ๋กค๋ฌ์ ๋ฉ์๋์ ์ ์ธ๋ ๋งค๊ฐ๋ณ์์ ๊ฐ์๊ฐ 0๊ฐ๋ผ๋ฉด ArgumentResolver๊ฐ ์ด๋ค ์ฒ๋ฆฌ๋ฅผ ํ  ํ์๊ฐ ์๋ค
            return EMPTY_ARGS;
        }

        Object[] args = new Object[parameters.length]; // ๋ง๋ค์ด์ผ ํ  ๋งค๊ฐ๋ณ์์ ์๋งํผ์ ๊ธธ์ด๋ฅผ ๊ฐ๋ ์ ์ ๋ฐฐ์ด์ ์์ฑํ๋ค
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter parameter = parameters[i];
            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
            args[i] = findProvidedArgument(parameter, providedArgs); // ์ปค์คํ ํ์ฅ์ ์ํด ์ด์ด๋ ๋ถ๋ถ์ผ๋ก ์ฌ๋ฃ๋๋ค
            if (args[i] != null) {
                continue;
            }
            if (!this.resolvers.supportsParameter(parameter)) { // ArgumentResolver๊ฐ ํด๋น ๋งค๊ฐ๋ณ์๋ฅผ ๋ง๋ค์ด๋ผ ์ ์๋์ง๋ฅผ ์ฒดํฌ
                throw new IllegalStateException(formatArgumentError(parameter, "No suitable resolver")); // ๋ง๋ค์ด๋ผ ์ ์๋ค๋ฉด ์์ธ๋ฅผ ๋์ง๋ค
            }
            try {
                // ์ค์ ๋ก ์ปจํธ๋กค๋ฌ์ ์ ๋ฌ๋  ๋งค๊ฐ๋ณ์๋ฅผ ๋ง๋ค์ด๋ด๋ ๋ถ๋ถ์ผ๋ก ๋ด๋ถ ๊ตฌํ์ ์ปค๋งจ๋ ํจํด์ผ๋ก ์ด๋ฃจ์ด์ ธ์๋ค.
                // resolveArgument()๋ HandlerMethodArgumentResolverComposite.getArgumentResolver()๋ฅผ ํธ์ถํ๋ค
                // getArgumentResolver()๋ ArgumentResolver๊ฐ ๋ค์ด์๋ List๋ฅผ ์ํํ๋ฉฐ resolver.supportsParameter()๋ฅผ ํธ์ถํ๋ค
                // ํด๋น ๋งค๊ฐ๋ณ์๋ฅผ ์์ฑ ํ  ์ ์๋ ArgumentResolver๋ฅผ ์ฐพ์ ๋ฐํํ๋ค. ์๋ค๋ฉด null์ ๋ฐํํ๋ค.
                // resolveArgument()๋ ๋ฐํ๋ฐ์ ArgumentResolver์ resolveArgument()๋ฅผ ํธ์ถํด ๋ฐ์ดํฐ๊ฐ ๋ฐ์ธ๋ฉ ๋ ๋งค๊ฐ๋ณ์ ์ธ์คํด์ค๋ฅผ ์์ฑํ๊ณ  ๋ฐํํ๋ค.
                args[i] = this.resolvers.resolveArgument(parameter, mavContainer, request, this.dataBinderFactory);
            }
            catch (Exception ex) {
                if (logger.isDebugEnabled()) {
                    String exMsg = ex.getMessage();
                    if (exMsg != null && !exMsg.contains(parameter.getExecutable().toGenericString())) {
                        logger.debug(formatArgumentError(parameter, exMsg));
                    }
                }
                throw ex;
            }
        }
        return args;
    }
}
```

<br />

```java
// file: 'HandlerMethodArgumentResolverComposite.class'
public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver { 
    
    ...

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

        HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter); // ๋งค๊ฐ๋ณ์๋ฅผ ์์ฑํด๋ผ ArgumentResolver๋ฅผ ๊ฐ์ ธ์จ๋ค 
        if (resolver == null) { // ๋งค๊ฐ๋ณ์๋ฅผ ์์ฑํ  ์ ์๋ ArgumentResolver๊ฐ ์๋ค๋ฉด ์์ธ๋ฅผ ๋์ง๋ค
            throw new IllegalArgumentException("Unsupported parameter type [" +
                parameter.getParameterType().getName() + "]. supportsParameter should be called first.");
        }
        return resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory); // ArgumentResolver๊ฐ ์กด์ฌํ๋ค๋ฉด ๋งค๊ฐ๋ณ์ ์์ฑ์ ์์ํ๋ค
    }

    @Nullable
    private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
        HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
        if (result == null) {
            for (HandlerMethodArgumentResolver resolver : this.argumentResolvers) {
                // ArgumentResolver๊ฐ ๋ค์ด์๋ List๋ฅผ ์ํํ๋ฉฐ ๋งค๊ฐ๋ณ์๋ฅผ ์์ฑํ  ์ ์๋ ArgumentResolver๋ฅผ ์ฐพ๋๋ค
                if (resolver.supportsParameter(parameter)) { 
                    result = resolver;
                    this.argumentResolverCache.put(parameter, result);
                    break;
                }
            }
        }
        return result;
    }

}
```

<br />

`@ModelAttribute`๋ฅผ ์ฒ๋ฆฌํ๋ `ArgumentResolver`๋ `ModelAttributeMethodProcessor`์ด๋ค.

<br />

```java
// file: 'ModelAttributeMethodProcessor.class'
@Override
@Nullable
public final Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
    NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

    Assert.state(mavContainer != null, "ModelAttributeMethodProcessor requires ModelAndViewContainer");
    Assert.state(binderFactory != null, "ModelAttributeMethodProcessor requires WebDataBinderFactory");

    // ์ปจํธ๋กค๋ฌ์ ์ ์ธ๋ ๋งค๊ฐ๋ณ์๋ช์ ๊ฐ์ ธ์จ๋ค. ์ปจํธ๋กค๋ฌ ๋งค๊ฐ๋ณ์์ Person person์ผ๋ก ์ ์ธํ์ผ๋ฏ๋ก "person"์ด ๋์ค๊ฒ ๋๋ค.
    String name = ModelFactory.getNameForParameter(parameter); 
    ModelAttribute ann = parameter.getParameterAnnotation(ModelAttribute.class); // @ModelAttribute๊ฐ ์ปจํธ๋กค๋ฌ ๋งค๊ฐ๋ณ์์ ์ ์ธ๋ผ์๋์ง ์ฒดํฌํ๋ค
    if (ann != null) { // @ModelAttribute๊ฐ ์๋ค๋ฉด ModelAndViewContainer์ ๋ณ๋์ ์ค์ ์ํ๋ค. ์ด ๋ถ๋ถ์ SSR์ View์ ๋ฐ์ดํฐ๊ฐ ๋ฐ์ธ๋ฉ๋๋ ๋ถ๋ถ์ ์ฒ๋ฆฌํ๋ ๊ฒ ๊ฐ๋ค.
        mavContainer.setBinding(name, ann.binding());
    }

    Object attribute = null; // ์ค์ ๋ก ์์ฑ๋  ๋งค๊ฐ๋ณ์ ์ธ์คํด์ค๋ฅผ ๋ด์ ๋ณ์
    BindingResult bindingResult = null; // ModelAndView ๋ฐ์ธ๋ฉ ๊ฒฐ๊ณผ๋ฅผ ์บก์ํํ ํด๋์ค

    if (mavContainer.containsAttribute(name)) { // ModelAndViewContainer์ person์ ํค๋ก ๊ฐ๋ ์ธ์คํด์ค๊ฐ ์กด์ฌํ๋ฉด ๊บผ๋ด์จ๋ค (HashMap)
        attribute = mavContainer.getModel().get(name);
    }
    else {
        try {
            attribute = createAttribute(name, parameter, binderFactory, webRequest); // ์ธ์คํด์ค๊ฐ ์๋ค๋ฉด ์ปจํธ๋กค๋ฌ์ ๋งค๊ฐ๋ณ์๊ฐ ๋  ์ธ์คํด์ค๋ฅผ ์๋ก ๋ง๋ค์ด์ผ ํ  ๊ฒ์ด๋ฏ๋ก ์์ฑํ๋ค
        }
        catch (BindException ex) {
            if (isBindExceptionRequired(parameter)) {
                throw ex;
        }
        if (parameter.getParameterType() == Optional.class) {
            attribute = Optional.empty();
        }
        else {
            attribute = ex.getTarget();
        }
        bindingResult = ex.getBindingResult();
        }
    }

    // ... ์๋ ๋ฉ์๋๋ก ์ ์ ์ด๋
}

protected Object createAttribute(String attributeName, MethodParameter parameter,
    WebDataBinderFactory binderFactory, NativeWebRequest webRequest) throws Exception {

    // ์์ฑํด์ผ ํ  ๋งค๊ฐ๋ณ์์ ํ์์ด Optional์ธ ๊ฒฝ์ฐ ๋ณ๋์ ์ฒ๋ฆฌ๋ฅผ ์งํํ๋๊ฑธ๋ก ๋ณด์ธ๋ค
    MethodParameter nestedParameter = parameter.nestedIfOptional();
    Class<?> clazz = nestedParameter.getNestedParameterType();

    // ์์ฑํด์ผ ํ  ๋งค๊ฐ๋ณ์์ ์์ฑ์๋ฅผ ๊ฐ์ ธ์จ๋ค. ๊ธฐ๋ณธ์ ์ผ๋ก ๊ธฐ๋ณธ์์ฑ์๋ฅผ ๊ฐ์ ธ์ค์ง๋ง, AllArgumentConstructor๊ฐ ์๋ค๋ฉด ์ด๊ฒ์ ๊ฐ์ ธ์จ๋ค.
    Constructor<?> ctor = BeanUtils.getResolvableConstructor(clazz);  
    
    // ๊ฐ์ ธ์จ ์์ฑ์์ ํด๋ผ์ด์ธํธ๊ฐ ๋ณด๋ธ ์์ฒญ ๋ฐ์ดํฐ๋ฅผ ๋ชจ๋ ๋ฐ์ธ๋ฉํ๋ค. ์์์ AllArgumentConstructor๊ฐ ์๋ ์์ฑ์๋ฅผ ๊ฐ์ ธ์๋ค๋ฉด ๋ณ๋์ Setter๊ฐ ํ์ํ๋ค.
    // Setter๊ฐ ์๋ค๋ฉด null์ด๋ ๊ธฐ๋ณธ๊ฐ์ผ๋ก ๋ฐ์ธ๋ฉํ๋ฉฐ, ์ธ์ ํ์์ด ๋ง์ง ์๋ค๋ฉด BindException์ ๋์ง๊ณ , ์์ฑ์๋ฅผ ํธ์ถํ์ง ๋ชปํ๋ค๋ฉด Exception์ ๋์ง๋ค.
    Object attribute = constructAttribute(ctor, attributeName, parameter, binderFactory, webRequest);
    
    if (parameter != nestedParameter) { 
        attribute = Optional.of(attribute);
    }
    return attribute;
}

@Override
@Nullable
public final Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
    
    // ... ๋ค์ ๋์์ด
    // ์ด ์์ ์์ ๊ธฐ๋ณธ์์ฑ์๋ฅผ ํธ์ถํ๊ธฐ ๋๋ฌธ์ attribute = Person(name=null, age=0) ์ด๋ฉฐ,
    // ๋ง์ฝ AllArgumentConstructor๋ฅผ ๊ฐ์ ธ์ ๋ง๋ค์๋ค๋ฉด ์ด ์์ ์์ Person(name=siro, age=11)์ด๋ค.
    // mavContainer์ ๋ํ ์ฒ๋ฆฌ๊ฐ ์์ง ์๋ฃ๋์ง ์์์ผ๋ฏ๋ก ์ด ์์ ์์ bindingResult๋ ํญ์ null ์ด๋ค.
    
    // ์ด์ฐ๋๋  ์ด ์์ ์์๋ attribute = Person(name=null, age=0)์ด๋ค.
        
    if (bindingResult == null) {
        WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
        if (binder.getTarget() != null) {
            if (!mavContainer.isBindingDisabled(name)) {
                bindRequestParameters(binder, webRequest); // Setter๋ฅผ ํธ์ถํด์ ๋ฐ์ดํฐ๋ฅผ ๋ชจ๋ ๋ฐ์ธ๋ฉํ๋ค. ์ด ์์ ์์ Person(name=siro, age=11)์ด๋ค.
            }
            validateIfApplicable(binder, parameter); // ์ ํจ์ฑ ๊ฒ์ฆ ๋ก์ง
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) { // ์ ํจ์ฑ ๊ฒ์ฆ ๋ก์ง
                throw new BindException(binder.getBindingResult());
            }
        }
        if (!parameter.getParameterType().isInstance(attribute)) { // ์ ํจ์ฑ ๊ฒ์ฆ ๋ก์ง
            attribute = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
        }
        bindingResult = binder.getBindingResult();
    }

    // ๋ง์ฝ ๋ฐํํ์์ด ModelAndView์ผ ๊ฒฝ์ฐ๋ฅผ ๋๋นํด ModelAndViewContainer์ ๋ฐ์ดํฐ๋ฅผ ํจ๊ป ๋ฐ์ธ๋ฉํด์ค๋ค
    Map<String, Object> bindingResultModel = bindingResult.getModel();
    mavContainer.removeAttributes(bindingResultModel);
    mavContainer.addAllAttributes(bindingResultModel);

    // ์ปจํธ๋กค๋ฌ์ ์ ๋ฌ๋์ด์ผ ํ  ๋งค๊ฐ๋ณ์๊ฐ ๋ง๋ค์ด์ก๊ณ , ๋ชจ๋  ๋ฐ์ดํฐ๊ฐ ๋ฐ์๋ฉ๋์๋ค. ์ด๋ฅผ ๋ฐํํ๋ค.
    return attribute;
}
```

<br />

# โจ RequestParamMethodArgumentResolver

---

๊ทธ์ค `RequestParamMethodArgumentResolver`๋ `@RequestParam`์ ์ฒ๋ฆฌํด์ฃผ๋ `HandlerMethodArgumentResolver`์ ์ฝํฌ๋ฆฌํธ ํด๋์ค ์ค ํ๋์ด๋ค.

`RequestParamMethodArgumentResolver`๋ ๋๋ถ๋ถ์ ์ฒ๋ฆฌ๋ฅผ ์์ ์ถ์ ํด๋์ค์ธ `AbstractNamedValueMethodArgumentResolver`์ ์์กดํ๋ฉฐ ํต์ฌ ์ฒ๋ฆฌ๋ ์์ ์ด ์ค๋ฒ๋ผ์ด๋ฉํ ๋ฉ์๋๋ฅผ ํตํด ์ฒ๋ฆฌํ๋ค.

์ญ์ ์ดํดํ ๋ด์ฉ์ ์ฝ๋์ ์ฃผ์์ผ๋ก ๋ฌ์๋ค.

<br />

```java
// file: 'AbstractNamedValueMethodArgumentResolver.class'
public abstract class AbstractNamedValueMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    @Nullable
    public final Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        
        NamedValueInfo namedValueInfo = getNamedValueInfo(parameter); // ์ฃผ์ด์ง ๋ฉ์๋ ๋งค๊ฐ๋ณ์์ ๋ํด ๋ช๋ช๋ ๊ฐ์ ์ป๋๋ค.
        MethodParameter nestedParameter = parameter.nestedIfOptional(); // ๋งค๊ฐ๋ณ์๊ฐ Optional๋ก ์ ์ธ๋ ๊ฒฝ์ฐ ๋ณ๋์ ์ฒ๋ฆฌ๋ฅผ ํ๊ณ , ์๋๊ฒฝ์ฐ ๊ทธ๋ฅ ๋ฐํํ๋ค.

        // ์ ํจ์ฑ ๊ฒ์ฌ
        Object resolvedName = resolveEmbeddedValuesAndExpressions(namedValueInfo.name); 
        if (resolvedName == null) {
            throw new IllegalArgumentException(
                "Specified name must not resolve to null: [" + namedValueInfo.name + "]");
        }

        // ์ด๊ณณ์์ RequestParamMethodArgumentResolver์ด ์ค๋ฒ๋ผ์ด๋ฉํ ๊ณณ์ผ๋ก ๋์ด๊ฐ๋ค.
        // ์ปจํธ๋กค๋ฌ์ ์ ์ธ๋ ๋งค๊ฐ๋ณ์ ํ์๊ณผ ๋ณ์๋ช์ ๋๊ธด๋ค.
        // ์ฒซ๋ฒ์งธ ์ธ์๋ ๋ณ์๋ช์ธ๋ฐ ์ฌ์ฉ์๊ฐ ๋ณด๋ธ ๋ณ์๋ช๊ณผ ์ปจํธ๋กค๋ฌ ๋ฉ์๋์ ์ ์ธ๋ ๋งค๊ฐ๋ณ์์ ๋ณ์๋ช์ ๋๋ค ์๋ฏธํ๋ค
        // ๋๋ฒ์งธ ์ธ์๋ ์ปจํธ๋กค๋ฌ์ ์ ์ธ๋ ๋งค๊ฐ๋ณ์๋ฅผ ์ถ์ํํ ํด๋์ค
        // ์ธ๋ฒ์งธ ์ธ์๋ ์ฌ์ฉ์์ ์์ฒญ ๊ทธ ์์ฒด๋ฅผ ์๋ฏธํ๋ค
        Object arg = resolveName(resolvedName.toString(), nestedParameter, webRequest);
        
        ...

        return arg;
    }
}
```

<br />

```java
// file: 'RequestParamMethodArgumentResolver.class'
public class RequestParamMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver
    implements UriComponentsContributor {
    
    // ์ด๋ค ๊ฒฝ์ฐ RequestParamMethodArgumentResolver๊ฐ ์ฒ๋ฆฌ๋ฅผ ์งํํ ์ง์ ๋ํ ์ฝ๋์ด๋ค
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
    
        // ๋งค๊ฐ๋ณ์์ @RequestParam ์ด ์ ์ธ๋ ๊ฒฝ์ฐ 
        if (parameter.hasParameterAnnotation(RequestParam.class)) {
	
            // @RequestParam Optional<E> var ์์ผ๋ก ์ ์ธ๋ ๊ฒฝ์ฐ ๋ณ๋์ ์ฒ๋ฆฌ๋ฅผ ํ๊ณ  ๊ฒฐ๊ณผ๋ฅผ ๋ฐํ
            if (Map.class.isAssignableFrom(parameter.nestedIfOptional().getNestedParameterType())) {
                RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
                return (requestParam != null && StringUtils.hasText(requestParam.name()));
            }
	    
            // @RequestParam์ด ๋ฌ๋ ค์์ผ๋ฉด์ Optional์ด ์๋ ๊ฒฝ์ฐ true๋ฅผ ๋ฐํ
            else {
                return true;
            }
        }
        else {
	
            // ๋งค๊ฐ๋ณ์์ @RequestPart๊ฐ ์ ์ธ๋ ๊ฒฝ์ฐ false๋ฅผ ๋ฐํ
            if (parameter.hasParameterAnnotation(RequestPart.class)) {
                return false;
            }

            // ๋งค๊ฐ๋ณ์์ @RequestPart๊ฐ ์ ์ธ๋ผ์์ง ์์ผ๋ฉด์, Optional์ด๊ณ  Multipart ๊ด๋ จ๋ ํ์์ธ ๊ฒฝ์ฐ true๋ฅผ ๋ฐํ
            parameter = parameter.nestedIfOptional();
            if (MultipartResolutionDelegate.isMultipartArgument(parameter)) {
                return true;
            }
            
            // RequestParamMethodArgumentResolver๋ useDefaultResolution๋ผ๋ ์ด๋ฆ์ boolean ์ํ๊ฐ์ ๊ฐ๋๋ค.
            // useDefaultResolution=false์ด๋ฉด @RequestParam์ด ์กด์ฌํ๋ ๊ฒฝ์ฐ ์ฒ๋ฆฌํ๋ค๋ ๋ป์ด๋ค
            // useDefaultResolution=true์ด๋ฉด ๊ฐ๋ฐ์๊ฐ @RequestParam์ ์๋ตํ ๊ฒฝ์ฐ ์ฒ๋ฆฌํ๋ค๋ ๋ป์ด๋ค
            
            // useDefaultResolution=true ์ธ ๊ฒฝ์ฐ์ด๋ฏ๋ก @RequestParam์ด ์์ผ๋ฉด์, SimpleProperty์ธ ๊ฒฝ์ฐ true๋ฅผ ๋ฐํํ๋ค๋ ๋ป์ด๋ค
            // ๋ฌธ์์ ์ํ๋ฉด SimpleProperty์ ์ ์๋ ๋ค์๊ณผ ๊ฐ๋ค
            // - primitive or primitive wrapper
            // - enum
            // - String or other CharSequence
            // - Number
            // - Date or Temporal            
            // - URI or URL
            // - Locale or a Class.
            else if (this.useDefaultResolution) { 
                return BeanUtils.isSimpleProperty(parameter.getNestedParameterType());
            }
            
            // ๋ชจ๋ ์๋๋ผ๋ฉด false๋ฅผ ๋ฐํํ๋ค (์์ ์ด ์ฒ๋ฆฌํ์ง ์๊ฒ ๋ค๋ ๋ป)
            else {
                return false;
            }
        }
    }
    @Override
    @Nullable
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);

        // ์ ์ธ๋ ๋งค๊ฐ๋ณ์๊ฐ Multipart ๊ด๋ จ๋ ํ์์ด๋ผ๋ฉด ์ฌ๊ธฐ์ ์ฒ๋ฆฌํ๋ค
        if (servletRequest != null) {
            Object mpArg = MultipartResolutionDelegate.resolveMultipartArgument(name, parameter, servletRequest);
            if (mpArg != MultipartResolutionDelegate.UNRESOLVABLE) {
                return mpArg;
            }
        }

        Object arg = null;

        // ์์ฒญ์ด Multipart ๊ด๋ จ๋ ํ์์ด๋ผ๋ฉด ์ฌ๊ธฐ์ ์ฒ๋ฆฌํ๋ค
        MultipartRequest multipartRequest = request.getNativeRequest(MultipartRequest.class);
        if (multipartRequest != null) {
            List<MultipartFile> files = multipartRequest.getFiles(name);
            if (!files.isEmpty()) {
                arg = (files.size() == 1 ? files.get(0) : files);
            }
        }
        
        // Multipart๊ฐ ์๋ ๊ฒฝ์ฐ๋ผ๋ฉด ์ฌ๊ธฐ์ ์ฒ๋ฆฌํ๋ค
        if (arg == null) {
            String[] paramValues = request.getParameterValues(name);
            if (paramValues != null) {
                arg = (paramValues.length == 1 ? paramValues[0] : paramValues);
            }
        }
	
        // ์์ฑ๋ ๋งค๊ฐ๋ณ์์ ์ธ์คํด์ค๋ฅผ ๋ฐํํ๋ค
        return arg;
    }
}
```

<br />

# ๐ค ์ ๋ฆฌ

---

- `@RequestParam`์ ์๋ตํ์ง ์๊ณ  ๋ถ์ฌ์ฃผ๋ฉด ์ธ๋ฐ์๋ ๋ฃจํ ์ํ๋ฅผ ์ค์ด๋๋ฐ ๋์์ ์ค๋ค.
- `@RequestParam`์ ์๋ตํ๋ฉด ์ค๋ ๋๋น ํ์์๋ ๋ฃจํ ์ํ๋ฅผ ์ ๊ฒ๋ ์์ญ๋ฒ, ๋ง๊ฒ๋ ์๋ฐฑ๋ฒ ๋ ๋์ง๋ง ์ฝ๋๊ฐ ๋ ๊ฐ๊ฒฐํด์ง๋ค.
- ์ฝ๋์์ผ๋ก ๋ณด๊ธฐ์ `@ModelAttribute`๊ฐ ํ๋ ์ผ์ด `ModelAndView`๋ฅผ ์ค์ ํ๋ ๊ฒ์ด ์ฃผ ๋ชฉ์ ์ผ๋ก ๋ณด์ด๋๋ฐ ์ด ๋ถ๋ถ์์ ์ฝ๊ฐ ํผ์ ์ด ์จ๋ค.
    - ์ค์ ๋ก `@ModelAttribute`๊ฐ ์์ด๋ ์ฟผ๋ฆฌ์คํธ๋ง์ผ๋ก ๋์ด์ค๋ ๋ฐ์ดํฐ๋ค์ ๋ฐ์ธ๋ฉ์ด ์์ฃผ ์ ๋๋ค.
    - ๊ฒฐ๊ตญ `@ModelAttribute`๊ฐ ์๊ณ  ์๊ณ ์ ์ฐจ์ด๋ `mavContainer(ModelAndViewContainer)`๋ฅผ ์ด๋ป๊ฒ ์ฒ๋ฆฌํ๋๊ฐ์ด๋ค.
    - ๊ทธ๋ ๋ค๋ฉด ๋ง์ฝ `SSR` ๋ฐฉ์์ด ์๋๊ณ  `CSR` ๋ฐฉ์์ด๋ผ `@RestController`๋ฅผ ์ฌ์ฉํ๋ค๋ฉด `@ModelAttribute`๋ฅผ ์๋ตํ๋ ๊ฒ์ด ์กฐ๊ธ ๋ ํจ์จ์ ์ผ๊น? `CSR` ๋ฐฉ์์ด๋ผ๋ฉด `ModelAndView`๋ฅผ ์ ๊ฒฝ์ฐ์ง ์์๋ .
        - ์ด๋ ๊ฒ ๋ณด๊ธฐ์ `RequestMappingHandlerAdapter`๊ฐ ์ฒ์์๋ `@ModelAttribute`๊ฐ ์๋ ๋งค๊ฐ๋ณ์๋ฅผ ์กฐํํ๊ณ , ๋ง์ง๋ง์๋ `@ModelAttribute`๊ฐ ์๋ ๋งค๊ฐ๋ณ์๋ฅผ ๋ค์ ์กฐํํ๋ค.
        - ๋ฐ๋ผ์ ์ด์ฐจํผ `@ModelAttribute`๊ฐ ์๋  ์๋  ๋ฌด์กฐ๊ฑด ์กฐํ๋๋ฏ๋ก ํจ์จ์ ์ด๋ผ๊ณ  ๋ณด๊ธฐ ํ๋ค ๊ฒ ๊ฐ๋ค.
        - ์ด๋ฐ ๊ตฌ์กฐ๋ก ๋ง๋  ์ด์ ๊ฐ ๋ฌด์์ผ๊น? ์ง๊ธ ๋ด ์์ค์ผ๋ก์  ์ง์ํ๊ธฐ ์ด๋ ต๋ค.

```java
// file: 'RequestMappingHandlerAdapter.class'
private List<HandlerMethodArgumentResolver> getDefaultArgumentResolvers() {
    List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>(30);

    // Annotation-based argument resolution
    resolvers.add(new RequestParamMethodArgumentResolver(getBeanFactory(), false)); // @RequestParam์ด ์๋ ๊ฒฝ์ฐ
    resolvers.add(new RequestParamMapMethodArgumentResolver());
    resolvers.add(new PathVariableMethodArgumentResolver());
    resolvers.add(new PathVariableMapMethodArgumentResolver());
    resolvers.add(new MatrixVariableMethodArgumentResolver());
    resolvers.add(new MatrixVariableMapMethodArgumentResolver());
    resolvers.add(new ServletModelAttributeMethodProcessor(false)); // @ModelAttribute๊ฐ ์๋ ๊ฒฝ์ฐ
    resolvers.add(new RequestResponseBodyMethodProcessor(getMessageConverters(), this.requestResponseBodyAdvice));
    resolvers.add(new RequestPartMethodArgumentResolver(getMessageConverters(), this.requestResponseBodyAdvice));
    resolvers.add(new RequestHeaderMethodArgumentResolver(getBeanFactory()));
    resolvers.add(new RequestHeaderMapMethodArgumentResolver());
    resolvers.add(new ServletCookieValueMethodArgumentResolver(getBeanFactory()));
    resolvers.add(new ExpressionValueMethodArgumentResolver(getBeanFactory()));
    resolvers.add(new SessionAttributeMethodArgumentResolver());
    resolvers.add(new RequestAttributeMethodArgumentResolver());
    
    ...

    // Type-based argument resolution
	
    ... 

    // Custom arguments
	
    ...

    // Catch-all
    resolvers.add(new PrincipalMethodArgumentResolver());
    resolvers.add(new RequestParamMethodArgumentResolver(getBeanFactory(), true)); // @RequestParam์ด ์๋ ๊ฒฝ์ฐ
    resolvers.add(new ServletModelAttributeMethodProcessor(true)); // @ModelAttribute๊ฐ ์๋ ๊ฒฝ์ฐ
	
    return resolvers;
}
```

<br />

- ์ ์  ํฉํ ๋ฆฌ ๋ฉ์๋๋ฅผ ์ฌ์ฉํด ์์ฑ์์ ์ ๊ทผ์ ํ์๊ฐ `private`์ด๋ `protected`๊ฐ ๋๋๋ผ๋ ์๊ด์๋ค. 
- ๋ฆฌํ๋ ์์ ํตํด ๋ณ๋์ ์ค์ ์ ํ๊ณ  ์ ๊ทผํ๊ธฐ ๋๋ฌธ์ ์ ๊ทผ๊ฐ๋ฅํ๋ค.
    - ์ฆ, ์์ฑ์๋ฅผ ์จ๊ธฐ๊ณ  ์ ์ ํฉํ ๋ฆฌ ๋ฉ์๋๋ฅผ ๋ธ์ถํด๋ ๋ฐ์ธ๋ฉ์ด ์์ฃผ ์ ๋๋ค.

<br />

- `์์ ์(Setter)`๋ฅผ ๋ฌด์กฐ๊ฑด ๋ฌ์์ผ ํ๋ ์ค ์์์ด์ ๊ฐ์ฒด์งํฅ์ ๊ณต๋ถํ๋ค ๋ณด๋ ์ด๊ฒ ๋งค์ฐ ๋ถ-ํธํ๋๋ฐ, **์ฝ๋๋ฅผ ๋ฏ์ด๋ณด๋ ์์ ์๊ฐ ํญ์ ํ์ํ๊ฑด ์๋๋ค.**
    - ์ฆ, ์์ฑ์๋ก ๋ฐ์ดํฐ ๋ฐ์ธ๋ฉ์ ์ปค๋ฒ์น  ์ ์๋ค๋ฉด ์์ ์๋ ์์ ์์ด๋ ๋๋ค
    - ๋ค๋ง `์ ๊ทผ์(Getter)`๋ ๋ฌด์กฐ๊ฑด ์์ด์ผ๋ง ํ๋๋ฐ, ์ด์ ๋ ๋ฐ์ดํฐ๋ฅผ ๋ฐํํ ๋ ๋ฐ์ดํฐ๋ฅผ ๊บผ๋ด์ผํ๊ธฐ ๋๋ฌธ์ด๋ค.
    - ์ ๊ทผ์๋ฅผ ์ ๊ฑฐํ๋๋ ํ๊ธฐ์ ๊ฐ์ ์์ธ๊ฐ ๋ฐ์ํ๋ค.

> DefaultHandlerExceptionResolver : Resolved [org.springframework.web.HttpMediaTypeNotAcceptableException: Could not find acceptable representation]

<br />

- ๊ต์ฅํ ์๊ธฐ์ง๋ง ํ๊ธฐ์ ๊ฐ์ ๋ฐฉ์์ผ๋ก๋ ๋ฐ์ธ๋ฉ์ด ๊ฐ๋ฅํ๋ค.
    - ์์ฑ์๋ฅผ ํตํด `String name`์ `siro`๋ฅผ ๋ฐ์ธ๋ฉํ๋ค.
    - ์์ ์๋ฅผ ํตํด `int age`์ `11`์ ๋ฐ์ธ๋ฉํ๋ค.

```java
@ToString
public class Person {

    private String name; // ์์ฑ์๋ฅผ ํตํด ๊ฐ์ฒด ์์ฑ๊ณผ ๋์์ ๋ฐ์ธ๋ฉ
    private int age; // ์ดํ ์์ ์๋ฅผ ํตํด ๋ฐ์ธ๋ฉ

    public Person(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
```

<br />
