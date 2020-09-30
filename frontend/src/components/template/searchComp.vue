<template>
  <v-container class="pa-0" fluid>
    <v-col class="pa-0">
      <v-img :src="require(`@/assets/main-smile.jpg`)" height="653">
        <div style="padding-top: 25px; width: 75%; margin: 150px auto;">
          <v-col style="width: 60%; margin: 0 auto;">
            <v-form action="/template/search">
              <v-text-field
                placeholder="일련번호를 입력하세요. 기부된 사진을 검색합니다."
                outlined
                color="#356859"
                clearable
                name="template"
                class="pa-0"
                background-color="white"
              />
            </v-form>
          </v-col>
        </div>
      </v-img>
    </v-col>
  </v-container>
</template>

<script>
export default {
    name: "searchComp",
    data: function() {
        return {

        }
    }
}
</script>