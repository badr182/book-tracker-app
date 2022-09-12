import { Directive, Input } from '@angular/core';
import { FormGroup, NG_VALIDATORS, ValidationErrors, Validator } from '@angular/forms';
import Validation from '../utils/validation';

@Directive({
  selector: '[appPasswordPattern]',
  providers: [{ provide: NG_VALIDATORS, useExisting: PasswordPatternDirective, multi: true }]

})
export class PasswordPatternDirective  implements Validator{

  constructor() { }

  @Input('appMatchPassword') matchPassword: string[] = [];
  validate(formGroup: FormGroup): ValidationErrors | null {    
    return Validation.match('password', 'confirmPassword')(formGroup);
    
  }

}
