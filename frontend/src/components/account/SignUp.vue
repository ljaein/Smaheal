<template>
  <v-container id="inspire" class="pr-15 pl-15">
    <v-row class="pa-0" no-gutters>
      <v-col class="pa-15">
        <v-sheet class="text-left text-h2 amber--text mb-7">
          SMAHEAL
        </v-sheet>
        <v-col class="pr-10 pl-10">
          <v-form ref="form" v-model="valid">

            <v-row justify="center">
              <v-col cols="2" class="text-center pt-8">
                * 아이디
              </v-col>
              <v-col>
                <v-text-field v-model="id" :counter="15" :rules="idRules" color="#356859"
                placeholder="사용할 아이디를 입력해주세요." required clearable></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col cols="2" class="text-center pt-8">
                * 비밀번호
              </v-col>
              <v-col>
                <v-text-field
                  v-model="password1"
                  type="password"
                  :counter="20"
                  :rules="passwordRules"
                  placeholder="비밀번호(영문자, 숫자 포함 8자 이상 입력해주세요.)"
                  required
                  clearable
                  color="#356859"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col cols="2" class="text-center pt-8">
                * 비밀번호 재입력
              </v-col>
              <v-col>
                <v-text-field
                  v-model="password2"
                  type="password"
                  :counter="20"
                  :rules="passwordRules"
                  placeholder="비밀번호를 재입력해주세요."
                  required
                  clearable
                  color="#356859"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col cols="2" class="text-center pt-8">
                * 이름
              </v-col>
              <v-col>
                <v-text-field v-model="name" :counter="15" :rules="nameRules" placeholder="이름을 입력해주세요." color="#356859" required clearable></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col cols="2" class="text-center pt-8">
                * 별명
              </v-col>
              <v-col>
                <v-text-field v-model="nickName" :counter="10" :rules="nickNameRules" placeholder="사용할 별명을 입력해주세요.(10자 내외)" color="#356859" required clearable></v-text-field>
              </v-col>
            </v-row>
            
            <v-col>
              * 하단 달력에서 생일을 입력해주세요.
            </v-col>
            <v-col>
              <v-date-picker v-model="birth" color="#356859"></v-date-picker>
            </v-col>

            <v-btn class="pa-5" :disabled="!valid" color="#356859" dark width="100%" @click="validate">회원가입</v-btn>
          </v-form>
          <div style="text-align: right">
            <v-btn text class="mt-3" @click="goLogin()">뒤로 가기</v-btn>
          </div>
        </v-col>
      </v-col>
    </v-row>
  </v-container>
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
    },
    goLogin: function() {
      this.$router.push('/login')
    }
  }
};
</script>

<style>
</style>