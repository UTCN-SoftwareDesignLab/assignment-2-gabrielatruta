<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>

      <v-btn @click="addUser">Add User</v-btn>
      <v-btn @click="viewBooks">Library</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "@/components/UserDialog";
import router from "@/router";

export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Email",
          align: "start",
          value: "email",
        },
        { text: "Username", value: "name" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editUser(user) {
      this.selectedItem = user;
      this.dialogVisible = true;
    },
    addUser() {
      this.dialogVisible = true;
    },

    viewBooks() {
      router.push("/books");
    },

    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.users = await api.users.allUsers();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
