<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="item.email" label="Email" />
          <v-text-field v-model="item.password" label="Password" />
          <v-text-field v-model="item.username" label="Username" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>

          <v-btn v-if="!isNew" @click="deleteItem">Delete</v-btn>

        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.users
            .create({
              email: this.item.email,
              password: this.item.password,
              username: this.item.username,
            })
            .then(() => this.$emit("refresh"));
      } else {
        api.users
            .edit({
              id: this.item.id,
              email: this.item.email,
              password: this.item.password,
              username: this.item.username,
            })
            .then(() => this.$emit("refresh"));
      }
    },
    deleteItem() {
      api.users.deleteById(this.item.id).then(() => this.$emit("refresh"));
    },

  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>
