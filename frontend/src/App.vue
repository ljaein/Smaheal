<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <v-img
          alt="Vuetify Logo"
          class="shrink mr-2"
          contain
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
          transition="scale-transition"
          width="40"
        />

        <v-img
          alt="Vuetify Name"
          class="shrink mt-1 hidden-sm-and-down"
          contain
          min-width="100"
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"
          width="100"
        />
      </div>

      <v-spacer></v-spacer>


      <v-btn
        @click="goDonationBoardDetail()"
        text
      >
        <span class="mr-2">ai_video test</span>
        <v-icon>mdi-open-in-new</v-icon>
      <v-btn @click="goLogin" text>
        <span class="mr-2">Login</span>
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
      }

    goLogin() {
      this.$router.push('/login'); 
    }
  }
};
</script>
