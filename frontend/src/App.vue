<template>

  <v-app>
    <v-app-bar
      app
      color="white"
      height="100"
    >
      <v-avatar
        class="mr-3"
        color="grey lighten-5"
        size="70"
      >
        <v-img
          contain
          max-height="70%"
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
        ></v-img>
      </v-avatar>

      <v-toolbar-title class="font-weight-black headline">
        VUETIFY
      </v-toolbar-title>

      <v-spacer></v-spacer>


      <v-btn
        @click="goDonationBoardDetail()"
        text
      >
        <span class="mr-2">ai_video test</span>
      </v-btn>

      <v-btn @click="goLogin" text>
        <span class="mr-2">Login</span>
      </v-btn>

      <v-btn text>
        <span class="mr-2">공지사항</span>
      </v-btn>

      <v-btn text>
        <span class="mr-2">기부 게시판</span>
      </v-btn>

      <v-btn text>
        <span class="mr-2">후기 게시판</span>
      </v-btn>

      <v-btn text>
        <span class="mr-2">my page</span>
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: "App",

  components: {
    
  },

  data: () => ({
    //
  }),
  methods: {
    goDonationBoardDetail: function() {
      // 경로 중복 문제가 콘솔에 안 뜨게 하기 위해서 catch 이용
      this.$router.push("/donationDetail").catch(() => {});
    },
    goLogin() {
      this.$router.push('/login'); 
    }
  }
};
</script>
