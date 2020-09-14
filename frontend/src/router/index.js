import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import DonationDetail from "../views/board/DonationDetail.vue"
import Login from "../views/account/Login.vue";
import noticeList from "../views/notice/noticeList.vue"

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
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;