/*
 * Copyright 2010 The Rabbit Eclipse Plug-in Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package rabbit.tracking.internal.workbench;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.core.commands.ExecutionEvent;
import org.joda.time.Instant;

import rabbit.tracking.Event;
import rabbit.tracking.workbench.ICommandEvent;

final class CommandEvent extends Event implements ICommandEvent {

  private final ExecutionEvent event;

  CommandEvent(Instant instant, ExecutionEvent executionEvent) {
    super(instant);
    this.event = checkNotNull(executionEvent, "executionEvent");
  }

  @Override public final ExecutionEvent execution() {
    return event;
  }

  @Override public String toString() {
    return toStringHelper()
        .add("executionEvent", execution())
        .toString();
  }
}
