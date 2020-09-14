<template>
  <div>
      <Login />
      <SignUp />
  </div>
</template>

<script>
import Login from "@/components/account/Login.vue";
import SignUp from "@/components/account/SignUp.vue";

export default {
    name: "login",
    components:{
        Login,
        SignUp
    }
}
</script>

<style>

</style>