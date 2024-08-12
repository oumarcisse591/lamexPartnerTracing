
// Fonction qui permet de donner l'equivalent du dollar en AED instantanement
//function multiplyByThree() {
//        // Récupérer la valeur du champ input
//        var inputValue = document.getElementById("inputNumber").value;
//
//        // Vérifier si l'entrée est un nombre valide
//        if (!isNaN(inputValue)) {
//          // Multiplier la valeur par 3
//          var result = parseFloat(inputValue) * 3.67;
//
//          // Afficher le résultat dans un élément HTML
//          document.getElementById("result").textContent = "L'equivalence en AED est " + result;
//        } else {
//          // Afficher un message d'erreur si l'entrée n'est pas un nombre valide
//          document.getElementById("result").textContent = "Veuillez saisir un nombre valide";
//        }
//}

// Fonction permettant de lire, capturer et effacer la signature
var canvas = document.querySelector("canvas");
      var signaturePad = new SignaturePad(canvas);

      document.getElementById("clear").addEventListener("click", function () {
          signaturePad.clear();
      });

      document.getElementById("save").addEventListener("click", function () {
          if (!signaturePad.isEmpty()) {
              var dataURL = signaturePad.toDataURL();
              document.getElementById("signature").value = dataURL;
          } else {
              alert("Mettez votre signature d'abord SVP");
          }
        });

        // Fonction qui permet de convertir de maniere instantane le AED et USD
function convertCurrency() {
        var amount = parseFloat(document.getElementById('transactionAmount').value);
        var currency = document.getElementById('currencySelect').value;
        var conversionRate = 3.67; // Taux de conversion de USD à AED
        var convertedAmount;

        if (isNaN(amount)) {
            document.getElementById('result').innerText = 'Veuillez entrer un montant valide.';
            return;
        }

        if (currency === 'AED') {
            convertedAmount = amount / conversionRate;
            document.getElementById('result').innerText = 'Montant Équivalent en USD : ' + convertedAmount.toFixed(2);
        } else {
            convertedAmount = amount * conversionRate;
            document.getElementById('result').innerText = 'Montant Équivalent en AED : ' + convertedAmount.toFixed(2);
        }
    }