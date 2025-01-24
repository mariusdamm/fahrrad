import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor() {
  }

  highlightInvalidInput(element: HTMLElement) {
    element.classList.add('invalid-input-shake', 'border', 'border-danger');
    setTimeout(() => {
      element.classList.remove('invalid-input-shake', 'border', 'border-danger');
    }, 500);
  }
}
