import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import DonationDetail from "../views/donation/DonationDetail.vue"
import DonationList from "../views/donation/DonationList.vue"
import Login from "../views/account/Login.vue";
import MyPage from "../views/account/MyPage.vue";
import ReviewDetail from "../views/review/ReviewDetail.vue";
import ReviewList from "../views/review/ReviewList.vue";
import ReviewWrite from "../views/review/ReviewWrite.vue";
import ReviewModify from "../views/review/ReviewModify.vue";
import noticeList from "../views/notice/noticeList.vue"
import noticeWrite from "../views/notice/noticeWrite.vue"
import noticeDetail from "../views/notice/noticeDetail.vue"
import DonationRequestDetail from "../views/donation/DonationRequestDetail.vue"
import SmileCreate from "../views/smile/SmileCreate.vue"
import DonationRequestCreate from "../views/donation/DonationRequestCreate.vue"
import imageTest from "../views/donation/imagetest.vue"
import noticeSearch from "../views/notice/noticeSearch.vue"
import noticeModify from "../views/notice/noticeModify.vue"
import signUp from "@/components/account/SignUp.vue"
import Voice from "../views/test/Voice.vue"
import SlideImage from "../views/template/SlideImage.vue"

Vue.use(VueRouter);

const routes = [{
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/imagetest",
        name: "imagetest",
        component: imageTest
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
        path: "/donationRequestCreate",
        name: "DonationRequestCreate",
        component: DonationRequestCreate
    },

    {
        path: "/login",
        name: "Login",
        component: Login
    },
    {
        path: "/myPage",
        name: "MyPage",
        component: MyPage
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
    {
        path: "/smileCreate/:ID",
        name: "SmileCreate",
        component: SmileCreate
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

    {
        path: "/notice/search",
        name: "noticeSearch",
        component: noticeSearch
    },

    {
        path: "/notice/modify/:noticeid",
        name: "noticeModify",
        component: noticeModify
    },

    {
        path: "/signup",
        name: "signUp",
        component: signUp,
    },
    {
        path: "/voice",
        name: "Voice",
        component: Voice,
    },
    {
        path: "/slideimage",
        name: "SlideImage",
        component: SlideImage
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;