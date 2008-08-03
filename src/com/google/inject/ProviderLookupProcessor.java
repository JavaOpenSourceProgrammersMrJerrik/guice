/**
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject;

import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.spi.ProviderLookup;

/**
 * Handles {@link Binder#getProvider} commands.
 *
 * @author crazybob@google.com (Bob Lee)
 * @author jessewilson@google.com (Jesse Wilson)
 */
class ProviderLookupProcessor extends AbstractProcessor {

  private final InjectorImpl injector;

  ProviderLookupProcessor(Errors errors, InjectorImpl injector) {
    super(errors);
    this.injector = injector;
  }

  @Override public <T> Boolean visitProviderLookup(ProviderLookup<T> command) {
    // ensure the provider can be created
    try {
      Provider<T> provider = injector.getProviderOrThrow(command.getKey(), errors);
      command.initDelegate(provider);
    } catch (ErrorsException e) {
      errors.merge(e.getErrors()); // TODO: source
    }

    return true;
  }
}