# Copyright (c) 2024 Digital Asset (Switzerland) GmbH and/or its affiliates. All rights reserved.
# SPDX-License-Identifier: Apache-2.0

# FROM https://github.com/pulumi/pulumi-kubernetes-operator/blob/master/Dockerfile
# used to control he underlyign pulumi version
ARG pulumi_version
FROM pulumi/pulumi:${pulumi_version}

ARG pulumi_version
LABEL org.opencontainers.image.base.name="pulumi/pulumi:${pulumi_version}"
# TODO(tech-debt) Clean up workaround for broken k8s apt repo (`EXPKEYSIG 234654DA9A296436` error)
RUN rm /etc/apt/sources.list.d/kubernetes.list

RUN apt-get update \
    && apt-get install tini \
    && apt-get clean

ENTRYPOINT ["tini", "--", "/usr/local/bin/pulumi-kubernetes-operator"]

# install operator binary
RUN wget -O - -c "https://github.com/pulumi/pulumi-kubernetes-operator/releases/download/v1.16.0/pulumi-kubernetes-operator_.1.16.0_.Linux_x86_64.tar.gz" | tar -xz -C /usr/local/bin/

COPY pulumi-override.sh /usr/bin/pulumi-override.sh
RUN useradd -m pulumi-kubernetes-operator
RUN mkdir -p /home/pulumi-kubernetes-operator/.ssh \
    && touch /home/pulumi-kubernetes-operator/.ssh/known_hosts \
    && chmod 700 /home/pulumi-kubernetes-operator/.ssh \
    && chown -R pulumi-kubernetes-operator:pulumi-kubernetes-operator /home/pulumi-kubernetes-operator/.ssh \
    && mv /usr/bin/pulumi /usr/bin/pulumi-original \
    && mv /usr/bin/pulumi-override.sh /usr/bin/pulumi \
    && chown pulumi-kubernetes-operator:pulumi-kubernetes-operator /usr/bin/pulumi \
    && chmod +x /usr/bin/pulumi

USER pulumi-kubernetes-operator

ENV XDG_CONFIG_HOME=/tmp/.config
ENV XDG_CACHE_HOME=/tmp/.cache
ENV XDG_CONFIG_CACHE=/tmp/.cache
ENV GOCACHE=/tmp/.cache/go-build
ENV GOPATH=/tmp/.cache/go

COPY target/LICENSE .
