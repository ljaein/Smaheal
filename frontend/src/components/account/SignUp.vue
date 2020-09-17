<template>
  <div id="app">
    <v-app id="inspire">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field v-model="id" :counter="15" :rules="idRules" label="아이디" required></v-text-field>

        <v-text-field
          v-model="password1"
          type="password"
          :counter="20"
          :rules="passwordRules"
          label="비밀번호"
          required
        ></v-text-field>

        <v-text-field
          v-model="password2"
          type="password"
          :counter="20"
          :rules="passwordRules"
          label="비밀번호 확인"
          required
        ></v-text-field>

        <v-text-field v-model="name" :counter="15" :rules="nameRules" label="이름" required></v-text-field>

        <v-text-field v-model="nickName" :counter="10" :rules="nickNameRules" label="별명" required></v-text-field>생일
        <v-date-picker v-model="birth"></v-date-picker>

        <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">회원가입</v-btn>
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
      birth: new Date().toISOString().substr(0, 10),
      valid: false,
      nameRules: [
        v => !!v || "이름을 입력해주세요",
        v => (v && v.length <= 15) || "이름은 최대 15글자까지만 사용할 수 있습니다"
      ],
      idRules: [
        v => !!v || "아이디를 입력해주세요",
        v => (v && v.length <= 15) || "아이디는 최대 15글자까지만 사용할 수 있습니다"
      ],
      nickNameRules: [
        v => !!v || "별명을 입력해주세요",
        v => (v && v.length <= 10) || "별명은 최대 10글자까지만 사용할 수 있습니다"
      ],
      passwordRules: [
        v => !!v || "비밀번호를 입력해주세요",
        v => (v && v.length <= 20) || "비밀번호는 최대 20글자까지만 사용할 수 있습니다"
      ],
    };
  },
  methods: {
    validate () {
      this.$refs.form.validate();
      if(this.password1 == this.password2){
        this.signUpRequest();
      }else{
        alert("비밀번호가 다릅니다");
      }
    },
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
            alert("성공적으로 가입 하였습니다.");
            this.$router.push("/");
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