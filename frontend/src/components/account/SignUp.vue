<template>
  <div id="app">
    <v-app id="inspire">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field v-model="id" :counter="15" :rules="idRules" label="아이디" required></v-text-field>

        <v-text-field v-model="name" :counter="15" :rules="nameRules" label="이름" required></v-text-field>

        <v-text-field
          v-model="password1"
          :counter="20"
          :rules="passwordRules"
          label="비밀번호"
          required
        ></v-text-field>

        <v-text-field
          v-model="password2"
          :counter="20"
          :rules="passwordRules"
          label="비밀번호 확인"
          required
        ></v-text-field>

        <v-text-field v-model="nickName" :counter="10" :rules="nickNameRules" label="별명" required></v-text-field>

        <v-text>생일</v-text>
        <v-date-picker v-model="birth"></v-date-picker>

        <v-btn :disabled="!valid" color="success" class="mr-4" @click="signUpRequest">회원가입</v-btn>

        <v-btn color="error" class="mr-4" @click="reset">취소</v-btn>
      </v-form>
    </v-app>
  </div>
</template>

<script>
import http2 from "@/util/http-common2.js";

export default {
  name: "SignUp",
  data() {
    return {
      id: "",
      name: "",
      password1: "",
      password2: "",
      nickName: "",
      birth: new Date().toISOString().substr(0, 10)
    };
  },
  methods: {
    signUpRequest() {
      http2
        .post("/signup", {
          userId: this.id,
          name: this.name,
          password: this.password1,
          nickName: this.nickName,
          birth: this.birth
        })
        .then(response => {
          if (
            response.data.success == true ||
            response.data.success == "true"
          ) {
            this.alertMsg =
              "성공적으로 가입 하였습니다. 이메일인증을 해주세요.";
            this.alert = true;
            this.visablelogin = true;
            this.submitted = true;
            this.newCustomer();
            this.$router.push("/emailauth");
          } else {
            this.alertMsg = response.data.message;
            this.alert = true;
            this.visablelogin = false;
            this.submitted = false;
          }
        })
        .catch(e => {
          if (e.request.status === 404) {
            this.alertMsg = "회원가입에 실패하였습니다.";
            this.alert = true;
          } else {
            this.visablelogin = false;
            this.submitted = false;
            this.newCustomer();
            this.$router.push(`/apierror/${e.request.status}/`);
          }
          console.log(e.request.status);
        });
    }
  }
};
</script>

<style>
</style>