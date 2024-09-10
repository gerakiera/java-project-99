package hexlet.code.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Faker faker;

    private User testUser;

    //private JwtRequestPostProcessor token;
}


//    @BeforeEach
//    public void setUp() {
//        testUser = Instancio.of(User.class)
//                .ignore(Select.field(Post::getId))
//                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
//                .create();
//
//        testPost = Instancio.of(Post.class)
//                .ignore(Select.field(Post::getId))
//                .supply(Select.field(Post::getName), () -> faker.gameOfThrones().house())
//                .supply(Select.field(Post::getBody), () -> faker.gameOfThrones().quote())
//                .supply(Select.field(Post::getAuthor), testUser)
//                .create();
//    }
//
//    @Test
//    public void testIndex() throws Exception {
//        postRepository.save(testPost);
//
//        var result = mockMvc.perform(get("/api/posts")
//                .andExpect(status()
//                        .isOk())
//                .andReturn();
//        var body = result.getResponse().getContentAsString();
//        assertThatJson(body).isArray();
//    }
//
//    @Test
//    public void testCreate() throws Exception {
//        var dto = postMapper.map(testPost);
//
//        var request = post("/api/posts")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto));
//
//        mockMvc.perform(request)
//                .andExpect(status().isCreated());
//
//        var post = postRepository.findBySlug(dto.getSlug()).get();
//        assertNotNull(post);
//        assertThat(post.getName()).isEqualTo(dto.getName());
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        postRepository.save(testPost);
//
//        var dto = new PostUpdateDTO();
//        dto.setName(JsonNullable.of("new name"));
//
//        var request = put("/api/posts/" + testPost.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto));
//
//        mockMvc.perform(request)
//                .andExpect(status().isOk());
//
//        post = postRepository.findById(testPost.getId()).get();
//        assertThat(post.getName()).isEqualTo(dto.getName());
//    }
//
//    @Test
//    public void testShow() throws Exception {
//        postRepository.save(testPost);
//
//        var request = get("/api/posts/" + testPost.getId());
//        var result = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn();
//        var body = result.getResponse().getContentAsString();
//        assertThatJson(body).and(
//                v -> v.node("slug").isEqualTo(testPost.getSlug()),
//                v -> v.node("name").isEqualTo(testPost.getName()),
//                v -> v.node("body").isEqualTo(testPost.getBody())
//        );
//    }
//
//    @Test
//    public void testDestroy() throws Exception {
//        postRepository.save(testPost);
//        var request = delete("/api/posts/" + testPost.getId());
//        mockMvc.perform(request)
//                .andExpect(status().isNoContent());
//
//        assertThat(postRepository.existsById(testPost.getId())).isEqualTo(false);
//    }
//}