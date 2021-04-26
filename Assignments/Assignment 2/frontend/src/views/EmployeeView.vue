<template>
  <v-card>
    <v-card-title>
      Book Store
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="items"
        :search="search"
        @click:row="editItem"
    ></v-data-table>
    <ItemDialogEmployee
        :opened="dialogVisible"
        :item="selectedItem"
        @refresh="refreshList"
    ></ItemDialogEmployee>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialogEmployee from "@/components/EmployeeViewDialog";

export default {
  name: "EmployeeView",
  components: {ItemDialogEmployee},
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },

    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allBooks();
    },
  },

  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
