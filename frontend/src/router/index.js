import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import DonationDetail from "../views/donation/DonationDetail.vue"
import Login from "../views/account/Login.vue";
import DonationRequestDetail from "../views/donation/DonationRequestDetail.vue"

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
        path: "/donationRequestDetail/:ID",
        name: "DonationRequestDetail",
        component: DonationRequestDetail
    },

    {
        path: "/login",
        name: "Login",
        component: Login
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;