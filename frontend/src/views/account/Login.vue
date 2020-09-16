<template>
  <div align="center">
    <v-tabs centered>
      <v-tabs-slider></v-tabs-slider>

      <v-tab>로그인</v-tab>
      <v-tab>회원가입</v-tab>

      <v-tab-item>
        <v-card flat tile width="300px">
          <Login />
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat tile width="300px">
          <SignUp />
        </v-card>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import Login from "@/components/account/Login.vue";
import SignUp from "@/components/account/SignUp.vue";

export default {
  name: "login",
  components: {
    Login,
    SignUp
  }
};
</script>

<style>
</style>