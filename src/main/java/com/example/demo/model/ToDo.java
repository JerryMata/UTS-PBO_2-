    package com.example.demo.model;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import jakarta.persistence.Table;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;


    @Entity
    @Table(name = "todos")
    public class ToDo {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull(message = "Task tidak boleh kosong")
        @Size(min = 1, max = 255, message = "Task harus antara 1 hingga 255 karakter")
        private String task;

        private boolean completed;

        @ManyToOne
        @JoinColumn(name = "id_user", nullable = false)
        private User user;

        public ToDo() {}

        public ToDo(String task, boolean completed) {
            this.task = task;
            this.completed = completed;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
