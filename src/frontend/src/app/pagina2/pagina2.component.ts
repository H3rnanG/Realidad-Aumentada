import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './pagina2.component.html',
  styleUrls: ['./pagina2.component.css']
})
export class Pagina2Component {
  title = 'proyecto';
  imageSrc: string | ArrayBuffer | null = null; // Asigna un valor inicial a imageSrc
  selectedFileName: string | undefined;
  videoStream: MediaStream | undefined;

  @ViewChild('fileInput') fileInput: ElementRef<HTMLInputElement> | undefined;
  @ViewChild('video') video: ElementRef<HTMLVideoElement> | undefined;

  constructor() { }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    this.selectedFileName = file ? file.name : undefined;
  }
  previewImage(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        // Verifica si e.target?.result no es undefined antes de asignar
        if (e.target?.result) {
          this.imageSrc = e.target.result;
        }
      };
      reader.readAsDataURL(file);
    }
  }

  openCamera(): void {
    navigator.mediaDevices.getUserMedia({ video: true })
      .then((stream) => {
        this.videoStream = stream;
        if (this.video) {
          this.video.nativeElement.srcObject = stream;
          this.video.nativeElement.play();
        }
        if (this.fileInput) {
          this.fileInput.nativeElement.disabled = true;
        }
      })
      .catch((error) => {
        console.error('Error al acceder a la cámara:', error);
      });
  }

  closeCamera(): void {
    if (this.videoStream) {
      this.videoStream.getTracks().forEach((track) => {
        track.stop();
      });
    }
    if (this.video) {
      this.video.nativeElement.srcObject = null;
    }
    if (this.fileInput) {
      this.fileInput.nativeElement.disabled = false;
    }
  }

  onSubmit(): void {

  }
}
