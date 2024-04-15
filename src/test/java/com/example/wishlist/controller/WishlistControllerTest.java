//package com.example.wishlist.controller;
//
//import com.example.wishlist.repository.WishlistJDBC;
//import com.example.wishlist.service.WishlistService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import org.springframework.security.test.context.support.WithMockUser;
//
//
//@WebMvcTest(WishlistController.class)
//class WishlistControllerTest {
//
//    @Autowired
////    private MockMvc mockMvc;
//
//    @MockBean
//    private WishlistService wishlistService;
//
//    @MockBean
//    private WishlistJDBC wishlistJDBC;
//
//    @Test
//    @WithMockUser(username = "user1")
//    void testGetWishlistMainPageByDefault() throws Exception {
//        mockMvc.perform(get("/wishlist"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("wishlist-main"));
//    }
//
//    @Test
//    @WithMockUser(username = "user1")
//    void testGetCreateWishlistPage() throws Exception {
//        mockMvc.perform(get("/wishlist/create"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("wishlist-create"));
//    }
//
//    @Test
//    @WithMockUser(username = "user1", roles = {"ADMIN"})
//    void testCreateWishlist() throws Exception {
//        mockMvc.perform(post("/wishlist/create"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("wishlist-main"));
//    }
//
//    @Test
//    @WithMockUser(username = "user1")
//    void showPageForAddingWish() throws Exception {
//        mockMvc.perform(get("/wishlist/???")) //database er tom for data derfor kan vi ikke showPage for en ønskeliste da den ikke findes i database. Derfor fejler test også?
//                .andExpect(status().isOk())
//                .andExpect(view().name("addWish"));
//    }
//
//
//    // SKAL MAN VÆRE ADMIN USER HER FOR AT FÅ ADGANG TIL AT POST?
//    @Test
//    @WithMockUser(username = "user1")
//    void addWishToWishlist() throws Exception {
//        mockMvc.perform(post("/wishlist/addWish"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("wishlist-main"));
//    }
//
//    @Test
//    @WithMockUser(username = "user1")
//    void viewWishlistByName() throws Exception {
//        mockMvc.perform(get("/wishlist/view/...")) //database er tom for data derfor kan vi ikke showPage for en ønskeliste da den ikke findes i database. Derfor fejler test også?
//                .andExpect(status().isOk())
//                .andExpect(view().name("viewWishlist"));
//    }
//
//    @Test
//    @WithMockUser(username = "user1")
//    void deleteWishFromWishlistOnName() throws Exception {
//        mockMvc.perform(get("/wishlist/delete/...")) //database er tom for data derfor kan vi ikke showPage for en ønskeliste da den ikke findes i database. Derfor fejler test også?
//                .andExpect(status().isOk())
//                .andExpect(view().name("wishlist-main"));
//    }
//}