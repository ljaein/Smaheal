<template>
  <div id="app">
    <v-app id="inspire">
      <v-form ref="form" lazy-validation>
        <v-text-field v-model="userId" label="아이디" required></v-text-field>

        <v-text-field v-model="password" type="password" label="비밀번호" required></v-text-field>

        <v-btn color="success" class="mr-4" @click="loginRequest">로그인</v-btn>
      </v-form>
    </v-app>
  </div>
</template>

<script>
import { AUTH_REQUEST } from "@/store/actions/auth";

export default {
  name: "Login",
  data() {
    return {
      userId: "",
      password: ""
    };
  },
  methods: {
    loginRequest() {
      //model에 바인딩된 데이터 모두 ==> this
      const { userId, password } = this;
      this.$store
        .dispatch(AUTH_REQUEST, { userId, password })
        .then(() => {
          this.userId = "";
          this.password = "";
          this.nowlogin = !this.nowlogin;
          this.$router.push("/");
        })
        .catch(e => {
          if (e.request.status === 404) {
            this.alertMsg =
              "로그인에 실패하였습니다. 아이디와 비밀번호를 확인하세요.";
            this.alert = true;
          } else {
            this.$emit("closeLoginModal");
            this.$router.push(`/apierror/${e.request.status}/`);
          }
        });
    }
  }
};
</script>

<style>
</style>