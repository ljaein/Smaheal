import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import DonationDetail from "../views/board/DonationDetail.vue"
import Login from "../views/account/Login.vue";
import noticeList from "../views/notice/noticeList.vue"
import noticeWrite from "../views/notice/noticeWrite.vue"
import noticeDetail from "../views/notice/noticeDetail.vue"

Vue.use(VueRouter);

const routes = [{
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/donationDetail",
        name: "DonationDetail",
        component: DonationDetail
    },

    {
        path: "/login",
        name: "Login",
        component: Login
    },

    {
        path: "/notice",
        name: "noticeList",
        component: noticeList
    },

    {
        path: "/notice/write",
        name: "noticeWrite",
        component: noticeWrite
    },

    {
        path: "/notice/detail/:noticeid",
        name: "noticeDetail",
        component: noticeDetail
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;