/*
 * Copyright 2010 The Rabbit Eclipse Plug-in Project
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
package rabbit.data.store.model;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jdt.core.IJavaElement;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;

/**
 * Represents a java element event.
 */
public class JavaEvent extends ContinuousEvent {
  
  @Nonnull
  private final IJavaElement element;

  /**
   * Constructs a new event.
   * 
   * @param endTime The end time of the event.
   * @param duration The duration in milliseconds.
   * @param element The java element of the event.
   * @throws IllegalArgumentException If duration is negative.
   * @throws NullPointerException If time is null, or element is null.
   */
  public JavaEvent(@Nonnull DateTime endTime, long duration, @Nonnull IJavaElement element) {
    super(endTime, duration);
    checkNotNull(element);
    this.element = element;
  }

  @Nonnull
  public IJavaElement getElement() {
    return element;
  }
}