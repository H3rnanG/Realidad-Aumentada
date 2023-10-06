document.ready(function (){
    
  console.log('Archivo Listo...');
      
});

async function analizar(){
  
  // Almaceno el archivo en una variable
  const fileInput = document.getElementById('fileInput');

  // Verifico hayan subido un archivo
  if (!fileInput.files || fileInput.files.length === 0) {
    alert('Por favor, seleccione un archivo.');
    return;
  }

  // Muestra la imagen original en el primer elemento <img>
  const originalImage = document.getElementById('originalImage');
  originalImage.src = URL.createObjectURL(fileInput.files[0]);

  // Creo un FormData y subo el archivo
  const formData = new FormData();
  formData.append('file', fileInput.files[0]);

  try {
    const response = await fetch('/api/analizar', {
      method: 'POST',
      body: formData,
    });

    if (response.ok) {
      // Convierte la respuesta en una imagen
      const blob = await response.blob();
      const imageUrl = URL.createObjectURL(blob);

      // Muestra la imagen redimensionada en el segundo elemento <img>
      const resizedImage = document.getElementById('resizedImage');
      resizedImage.src = imageUrl;
    } else {
      console.error('Error en la solicitud:', response.statusText);
    }
  } catch (error) {
    console.error('Error al enviar la solicitud:', error);
  }
}