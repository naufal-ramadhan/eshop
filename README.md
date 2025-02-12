# eshop

---
Nama    : Muhammad Naufal Ramadhan <br>
NPM     : 2306241700 <br>
Kelas   : B <br>

---

<details>
  <summary>
    Module 1
  </summary>

### Reflection 1
---
##### You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean   code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code. Please write your reflection inside the repository's README.md file.
---

Jawaban :
Beberapa clean code standards yang sudah saya terapkan adalah Meaningful Names, Function, dan Error Handling.
* Meaningful Names :

public Product findById(String Id){
return productRepository.findById(Id);
}

Nama sudah menjelaskan melakukan pencarian produk berdasarkan Id.

* Function

public Product delete(Product product) {
productData.remove(product);
return product;
}

Funcion delete hanya melakukan 1 hal yaitu delete dengan sekecil mungkin.

* Error Handling

public String updateProductPost(@ModelAttribute Product updatedProduct, Model model) {
try{
service.update(updatedProduct);
} catch (IllegalStateException e){
model.addAttribute("error", "Quantity Cannot be Negative or Name cannot be empty");
return "updateProduct";
} catch (NoSuchElementException e) {
model.addAttribute("error", "Product not found");
return "updateProduct";
}
return "redirect:list";
}

Method update pada service akan throws 2 exception yang akan dihandle pada method tersebut.

Untuk Secure Coding standards yang sudah saya terapkan adalah input validation.

public Product update(Product updatedProduct) throws IllegalStateException, NoSuchElementException{
if (updatedProduct.getProductQuantity() < 0 || updatedProduct.getProductName().isEmpty()){
throw new IllegalStateException("Product quantity cannot be less than 0");
}

    Product product = productRepository.findById(updatedProduct.getProductId());
    if (product == null) {
        throw new NoSuchElementException("Product not found");
    }

    product.setProductQuantity(updatedProduct.getProductQuantity());
    product.setProductName(updatedProduct.getProductName());
    return productRepository.update(product);
}

Disini dilakukan validasi input contohnya pada quantity produk dan nama di service ketika melakukan update produk, Sehingga terhindar dari value yang tidak sesuai.

Kesalahan yang saya lihat pada source code saya adalah belum ada sanitasi untuk input, sehingga memungkinkan terjadinya Injeksi script (XSS). Untuk improve nya harus dilakukan sanitasi pada backend yang akan membersihkan special character yang bisa terpakai pada command-command tertentu.
</details>