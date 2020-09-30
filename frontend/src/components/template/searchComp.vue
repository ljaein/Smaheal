<template>
    <div>
      <v-col>
        <v-form action="/template/search">
          <v-text-field
            placeholder="일련번호를 입력해주세요."
            outlined
            color="#356859"
            clearable
            name="template"
            class="pa-3"
          />
        </v-form>
      </v-col>
    </div>
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