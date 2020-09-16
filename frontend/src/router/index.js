import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import DonationDetail from "../views/donation/DonationDetail.vue"
import DonationList from "../views/donation/DonationList.vue"
import Login from "../views/account/Login.vue";
import ReviewDetail from "../views/review/ReviewDetail.vue";
import ReviewList from "../views/review/ReviewList.vue";
import ReviewWrite from "../views/review/ReviewWrite.vue";
import ReviewModify from "../views/review/ReviewModify.vue";
import noticeList from "../views/notice/noticeList.vue"
import DonationRequestDetail from "../views/donation/DonationRequestDetail.vue"

Vue.use(VueRouter);

const routes = [{
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/donationList",
        name: "DonationList",
        component: DonationList
    },
    {
        path: "/donationDetail",
        name: "DonationDetail",
        component: DonationDetail
    },
    {
        path: "/donationRequestDetail/:ID",
        name: "DonationRequestDetail",
        component: DonationRequestDetail
    },

    {
        path: "/login",
        name: "Login",
        component: Login
    },
    {
        path: "/reviewDetail/:num",
        name: "ReviewDetail",
        component: ReviewDetail
    },
    {
        path: "/reviewList",
        name: "ReviewList",
        component: ReviewList
    },
    {
        path: "/reviewWrite",
        name: "ReviewWrite",
        component: ReviewWrite
    },
    {
        path: "/reviewModify/:num",
        name: "ReviewModify",
        component: ReviewModify
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