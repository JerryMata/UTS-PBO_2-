# UTS Pemrograman Berorientasi Obyek 2
<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 2</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Profil
<ul>
  <li>Nama: {Yeremia Adrianto Simarmata}</li>
  <li>NIM: {23552011227}</li>
  <li>Studi Kasus: {To Do List yang bisa diedit}</li>
</ul>

## Judul Studi Kasus
<p>UTS PBO 2  aplikasi Todo List sederhana dengan fitur login pengguna dan manajemen tugas pribadi.</p>

## Penjelasan Studi Kasus
<p>Aplikasi Todo list sederhana dengan fitur
1. Login akun
2. Register user
3. Lihat list todo list yang disesuaikan dengan user
4. logout
5. edit todo list</p>

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<p>Inheritance adalah konsep di mana sebuah class dapat mewarisi properti dan method dari class lain. Dalam studi kasus disini  memiliki inheritance dari framework yang saya pakai. Contohnya adalah class TodoRepository yang mewarisi perilaku dari Spring Data JPA (meng-extends JpaRepository) dan class-controller yang menggunakan anotasi seperti @Controller dari Spring Framework.</p>

### 2. Encapsulation
<p>Encapsulation adalah membungkus data (field) dan method dalam satu unit (class), serta membatasi akses ke data tersebut. Contoh encapsulation dalam studi kasus disini adalah pada class Todo dan class User di mana beberapa field seperti username, name, dan password dideklarasikan sebagai private. Data tersebut hanya bisa diakses dan dimodifikasi melalui method setter dan getter yang disediakan.</p>

### 3. Polymorphism
<p>Polymorphism adalah kemampuan objek untuk mengambil banyak bentuk, biasanya melalui interface atau inheritance. Contoh polymorphism dalam studi kasus disini adalah class UserService yang mengoverride method dari interface UserDetailsService milik Spring Security. Ini adalah contoh polymorphism, di mana objek UserService bisa diperlakukan sebagai instance dari UserDetailsService.</p>

### 4. Abstract
<p>Abstraction adalah menyembunyikan detail implementasi dan hanya menampilkan fungsionalitas penting. Contoh dalam studi kasus disini adalah UserRepository dan TodoRepository yang merupakan interface dari abstract class di Spring Data JPA. Saya hanya menggunakan method-method yang tersedia tanpa perlu mengetahui detail implementasinya secara langsung.</p>

## Demo Proyek
<ul>
  <li>Github: <a href="">Github</a></li>
  <li>Youtube: <a href="">Youtube</a></li>
</ul>
