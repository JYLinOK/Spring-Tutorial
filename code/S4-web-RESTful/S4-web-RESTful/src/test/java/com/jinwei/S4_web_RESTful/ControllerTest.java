package com.jinwei.S4_web_RESTful;
// 导入 junit.jupiter.api.Test API测试库
import org.junit.jupiter.api.Test;
// 导入 junit.jupiter.api.Test BeforeEach 测试库
import org.junit.jupiter.api.BeforeEach;
// 导入 SpringBoot Bean 自动布线
import org.springframework.beans.factory.annotation.Autowired;
// 导入 SpringBoot 自动配置的Web服务的自动配置MockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// 导入 SpringBootTest测试模块
import org.springframework.boot.test.context.SpringBootTest;
// 导入 SpringBoot http数据媒体模块
import org.springframework.http.MediaType;
// 导入 SpringBoot Web服务的MockMvc配置
import org.springframework.test.web.servlet.MockMvc;
// 导入 SpringBoot Web服务的MockMvc的请求绑定
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// 导入 SpringBoot Web服务的MockMvc的结果匹配器
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// 导入 Hamcrest测试匹配器对应函数
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
//导入 SpringBoot Web服务的print，打印出测试请求的返回信息
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// 使用@SpringBootTest注解：说明是SpringBoot测试
@SpringBootTest
// 使用@AutoConfigureMockMvc注解：开始设置自动MockMvc配置
@AutoConfigureMockMvc
// 定义ControllerTest控制器测试类
public class ControllerTest {

    @Autowired  // 使用@Autowired注解定义mockMvc对象
    private MockMvc mockMvc;

    @BeforeEach  // 使用@BeforeEach注解定义开始启动的项目：开始启动处理
    public void setUp() {
        // Clear messages before each test
        // This ensures a clean state for each test
        // Alternatively, you could use a test database or mock data
        // depending on your requirements
        Controller messagesController = new Controller();  // 首先创建一个控制器对象
        messagesController.getMessages().clear();  // 清空控制器存储信息
    }

    // 下面逐个通过@Test注解定义所有需要进行的测试
    @Test
    public void testBeforeEach1() {
        System.out.println("test @Test ================> 1");
    }

    @Test
    public void testBeforeEach2() {
        System.out.println("test @Test ================> 2");
    }

    // 上述两个函数用于测试 @Test注解，下面的是正式的测试函数

    @Test
    // 测试不带参数的 Get命令-接口运行
    // MockMvc是由 org.springframework.test 包提供，实现了对Http请求的模拟，
    // 能够直接通过使用模拟网络的形式，调用Controller，测试速度快、也不依赖网络环境，
    // 同时MockMvc也提供了一套验证的工具，方便对测试结果进行验证。

    // 测试简单Get功能
    public void testSayHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello")).andDo(print())  // 后面添加的.andDo(print())方法可以打印出测试请求的返回信息
                .andExpect(MockMvcResultMatchers.status().isOk())  // 如果返回请求状态是正常的
                .andExpect(MockMvcResultMatchers.content().string("Hello, Spring Boot!"));  // 则返回内容主体为设置的字符串

        System.out.println("------------------------------------------------->");
    }

    @Test
    // 测试Get功能
    public void testGetMessages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/messages")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
        System.out.println("------------------------------------------------->");
    }

    @Test
    // 测试添加功能
    public void testAddMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Test Message\"")).andDo(print())
                        .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("------------------------------------------------->");
    }

    @Test
    // 测试更新功能
    public void testUpdateMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"Initial Message\"")).andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/messages/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Updated Message\"")).andDo(print()).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    // 测试删除功能
    public void testDeleteMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"Message to Delete\"")).andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/messages/0")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}